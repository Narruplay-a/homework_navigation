package ru.otus.cookbook.data

/**
 * Filter state
 */
data class FilterState(
    val query: String = "",
    val categories: List<Pair<RecipeCategory, Boolean>> = emptyList()
)