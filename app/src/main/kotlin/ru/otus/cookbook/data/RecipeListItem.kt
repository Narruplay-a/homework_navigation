package ru.otus.cookbook.data

import androidx.annotation.LayoutRes
import ru.otus.cookbook.R

/**
 * Recipe list items.
 */
sealed class RecipeListItem : WithLayoutId {
    /**
     * Recipe item.
     */
    data class RecipeItem(private val recipe: Recipe) : RecipeListItem(), WithLayoutId by RecipeItem {
        companion object : WithLayoutId {
            @get:LayoutRes
            override val layoutId: Int = R.layout.vh_recipe_item
        }

        val id: Int get() = recipe.id
        val title: String get() = recipe.title
        val description: String get() = recipe.description
        val imageUrl: String get() = recipe.imageUrl
    }

    /**
     * Category item.
     */
    data class CategoryItem(val category: RecipeCategory) : RecipeListItem(), WithLayoutId by CategoryItem {
        companion object : WithLayoutId {
            @get:LayoutRes
            override val layoutId: Int = R.layout.vh_recipe_category
        }

        val name: String get() = category.name
    }
}