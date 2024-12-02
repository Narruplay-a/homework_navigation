package ru.otus.cookbook.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

/**
 * Provides access to the list of recipes.
 */
class RecipeRepository(recipes: List<Recipe>) {

    private val nextId = recipes.maxOfOrNull { it.id }?.plus(1) ?: 1
    private val recipes = MutableStateFlow(recipes)

    /**
     * Returns the list of recipes as a flow.
     * @param scope The coroutine scope to use for the flow.
     * @param filter The filter to apply to the recipes.
     */
    suspend fun getRecipes(scope: CoroutineScope, filter: RecipeFilter): StateFlow<List<Recipe>> = recipes
        .map { recipes -> recipes.asSequence()
            .filter { filter.categories.isEmpty() || filter.categories.contains(it.category) }
            .filter { filter.query.isNullOrBlank() || it.title.contains(filter.query, ignoreCase = true) }
            .toList()
        }
        .stateIn(scope)

    /**
     * Returns the list of categories as a flow.
     * @param scope The coroutine scope to use for the flow.
     */
    suspend fun getCategories(scope: CoroutineScope): StateFlow<List<RecipeCategory>> = recipes
        .map { recipes ->  recipes.map { it.category }.distinct().sorted() }
        .stateIn(scope)

    /**
     * Returns the recipe with the specified ID.
     */
    fun getRecipe(id: Int): Recipe? = recipes.value.find { it.id == id }

    /**
     * Returns the list of recipes as a flow.
     * @param id The ID of the recipe to delete.
     */
    fun deleteRecipe(id: Int) {
        recipes.update { list ->
            list.filter { it.id != id }
        }
    }

    /**
     * Adds a recipe to the list.
     * @param recipe The recipe to add.
     */
    fun addRecipe(recipe: Recipe) {
        recipes.update { list ->
            list + recipe.copy(id = nextId)
        }
    }
}
