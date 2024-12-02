package ru.otus.cookbook.data

import junit.framework.TestCase.assertEquals
import org.junit.Test
import ru.otus.cookbook.data.RecipeListItem.CategoryItem
import ru.otus.cookbook.data.RecipeListItem.RecipeItem
import ru.otus.cookbook.mockRecipes

class GetRecipeListKtTest {
    @Test
    fun `transforms recipe list to recipe list items`() {
        val recipes = mockRecipes.shuffled()

        val result = recipes.toRecipeListItems()

        val expected = listOf(
            CategoryItem(RecipeCategory("Category 1")),
            RecipeItem(mockRecipes[0]),
            CategoryItem(RecipeCategory("Category 2")),
            RecipeItem(mockRecipes[1]),
            CategoryItem(RecipeCategory("Category 3")),
            RecipeItem(mockRecipes[2])
        )

        assertEquals(expected, result)
    }
}