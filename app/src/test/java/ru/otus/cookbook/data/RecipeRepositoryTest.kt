package ru.otus.cookbook.data

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import ru.otus.cookbook.mockRecipes
import ru.otus.cookbook.newRecipe

@OptIn(ExperimentalCoroutinesApi::class)
class RecipeRepositoryTest {

    private lateinit var repository: RecipeRepository

    @Before
    fun init() {
        repository = RecipeRepository(mockRecipes)
    }

    @Test
    fun `returns a list of categories`() = runTest(UnconfinedTestDispatcher()) {
        val categories = repository.getCategories(backgroundScope).first()

        val expected = listOf(
            RecipeCategory("Category 1"),
            RecipeCategory("Category 2"),
            RecipeCategory("Category 3")
        )

        assertEquals(expected, categories)
    }

    @Test
    fun `returns a list of unfiltered items`() = runTest(UnconfinedTestDispatcher()) {
        val recipes = repository.getRecipes(backgroundScope, RecipeFilter()).first()

        val expected = listOf(
            mockRecipes[0],
            mockRecipes[1],
            mockRecipes[2]
        )

        assertEquals(expected, recipes)
    }

    @Test
    fun `filters by title`() = runTest(UnconfinedTestDispatcher()) {
        val recipes = repository.getRecipes(backgroundScope, RecipeFilter("2")).first()

        val expected = listOf(
            mockRecipes[1],
        )

        assertEquals(expected, recipes)
    }

    @Test
    fun `filters by category`() = runTest(UnconfinedTestDispatcher()) {
        val recipes = repository.getRecipes(
            backgroundScope,
            RecipeFilter(
                null,
                setOf(
                    RecipeCategory("Category 1"),
                    RecipeCategory("Category 3")
                )
            )
        ).first()

        val expected = listOf(
            mockRecipes[0],
            mockRecipes[2]
        )

        assertEquals(expected, recipes)
    }

    @Test
    fun `filters by title and category`() = runTest(UnconfinedTestDispatcher()) {
        val recipes = repository.getRecipes(
            backgroundScope,
            RecipeFilter(
                "2",
                setOf(RecipeCategory("Category 2"))
            )
        ).first()

        val expected = listOf(
            mockRecipes[1]
        )

        assertEquals(expected, recipes)
    }

    @Test
    fun `deletes recipe`() = runTest(UnconfinedTestDispatcher()) {
        val recipes = backgroundScope.async {
            repository.getRecipes(backgroundScope, RecipeFilter()).take(2).toList()
        }

        repository.deleteRecipe(2)

        val expected = listOf(
            listOf(
                mockRecipes[0],
                mockRecipes[1],
                mockRecipes[2]
            ),
            listOf(
                mockRecipes[0],
                mockRecipes[2]
            )
        )

        assertEquals(expected, recipes.await())
    }

    @Test
    fun `adds recipe`() = runTest(UnconfinedTestDispatcher()) {
        val recipes = backgroundScope.async {
            repository.getRecipes(backgroundScope, RecipeFilter()).take(2).toList()
        }

        repository.addRecipe(newRecipe)

        val expected = listOf(
            listOf(
                mockRecipes[0],
                mockRecipes[1],
                mockRecipes[2]
            ),
            listOf(
                mockRecipes[0],
                mockRecipes[1],
                mockRecipes[2],
                newRecipe.copy(id = 4)
            )
        )

        assertEquals(expected, recipes.await())
    }
}