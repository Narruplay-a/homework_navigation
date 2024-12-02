package ru.otus.cookbook.data

/**
 * Represents a filter for recipes.
 */
data class RecipeFilter(
    val query: String? = null,
    val categories: Set<RecipeCategory> = emptySet()
)