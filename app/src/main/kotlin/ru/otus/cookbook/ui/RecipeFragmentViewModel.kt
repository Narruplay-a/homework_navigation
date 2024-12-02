package ru.otus.cookbook.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.otus.cookbook.App
import ru.otus.cookbook.data.Recipe
import ru.otus.cookbook.data.RecipeRepository


class RecipeFragmentViewModel(private val repository: RecipeRepository, private val recipeId: Int) : ViewModel() {

    private val mRecipe = MutableStateFlow(
        checkNotNull(repository.getRecipe(recipeId)) { "Recipe with ID $recipeId not found" }
    )
    /**
     * The recipe.
     */
    val recipe: StateFlow<Recipe> = mRecipe.asStateFlow()

    /**
     * Deletes the recipe.
     */
    fun delete() {
        repository.deleteRecipe(recipeId)
    }

    companion object {
        /**
         * Key for the recipe ID.
         */
        val RECIPE_ID_KEY = object : CreationExtras.Key<Int> {}

        /**
         * Factory for creating [CookbookFragmentViewModel].
         */
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                RecipeFragmentViewModel(
                    (get(APPLICATION_KEY) as App).recipeRepository,
                    requireNotNull(get(RECIPE_ID_KEY)) { "Recipe ID must be provided" }
                )
            }
        }
    }
}