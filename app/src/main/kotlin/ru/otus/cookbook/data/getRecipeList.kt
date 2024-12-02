package ru.otus.cookbook.data

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.otus.cookbook.data.RecipeListItem.CategoryItem
import ru.otus.cookbook.data.RecipeListItem.RecipeItem


/**
 * Transforms the list of recipes into a list of recipe list items.
 */
suspend fun RecipeRepository.getRecipeList(scope: CoroutineScope, filter: RecipeFilter = RecipeFilter()): StateFlow<List<RecipeListItem>> =
    getRecipes(scope, filter)
        .map { recipes -> recipes.toRecipeListItems() }
        .stateIn(scope)

/**
 * Converts the list of recipes into a list of recipe list items.
 */
@VisibleForTesting
fun List<Recipe>.toRecipeListItems(): List<RecipeListItem> =
    groupBy { it.category }
        .entries
        .sortedBy { it.key }
        .map { (category, recipes) ->
            listOf(CategoryItem(category)) + recipes.map { RecipeItem(it) }.sortedBy { it.title }
        }
        .flatten()