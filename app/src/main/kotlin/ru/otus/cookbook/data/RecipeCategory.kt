package ru.otus.cookbook.data

/**
 * Recipe category data class
 */
@JvmInline
value class RecipeCategory(val name: String): Comparable<RecipeCategory> {
    override fun compareTo(other: RecipeCategory): Int = name.compareTo(other.name)
}