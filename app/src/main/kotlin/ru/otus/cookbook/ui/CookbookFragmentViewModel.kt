package ru.otus.cookbook.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import ru.otus.cookbook.App
import ru.otus.cookbook.data.RecipeCategory
import ru.otus.cookbook.data.RecipeFilter
import ru.otus.cookbook.data.RecipeListItem
import ru.otus.cookbook.data.RecipeRepository
import ru.otus.cookbook.data.getRecipeList

/**
 * View model for the cookbook fragment.
 * - Subscribe `recipeList` for the filtered recipe list.
 * - Subscribe `filter` for the filter to apply to the recipe list.
 * - Call `setSearchQuery` to set the search query to filter the recipe list.
 * - Call `toggleCategory` to toggle the specified category in the filter.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class CookbookFragmentViewModel(private val repository: RecipeRepository) : ViewModel() {

    private val mFilter = MutableStateFlow(RecipeFilter())

    /**
     * Filter to apply to the recipe list.
     */
    val filter: StateFlow<RecipeFilter> = mFilter.asStateFlow()

    /**
     * Sets the search query to filter the recipe list.
     */
    fun setSearchQuery(query: String) {
        mFilter.update { it.copy(query = query) }
    }

    /**
     * Toggles the specified category in the filter.
     */
    fun toggleCategory(category: RecipeCategory) {
        mFilter.update {
            it.copy(
                categories = if (category in it.categories) {
                    it.categories - category
                } else {
                    it.categories + category
                }
            )
        }
        mFilter.value = mFilter.value.copy(categories = mFilter.value.categories.toMutableSet().apply {
            if (category in this) {
                remove(category)
            } else {
                add(category)
            }
        })
    }


    /**
     * Recipe list to display in the UI.
     */
    val recipeList: StateFlow<List<RecipeListItem>> = mFilter
        .asStateFlow()
        .flatMapLatest {
            repository.getRecipeList(viewModelScope, it)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )

    companion object {
        /**
         * Factory for creating [CookbookFragmentViewModel].
         */
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                CookbookFragmentViewModel((get(APPLICATION_KEY) as App).recipeRepository)
            }
        }
    }
}