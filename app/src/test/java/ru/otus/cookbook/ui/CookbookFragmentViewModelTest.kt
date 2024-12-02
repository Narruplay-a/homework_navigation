package ru.otus.cookbook.ui

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.otus.cookbook.data.FilterState
import ru.otus.cookbook.data.RecipeCategory
import ru.otus.cookbook.data.RecipeRepository
import ru.otus.cookbook.data.toRecipeListItems
import ru.otus.cookbook.mockRecipes

@ExperimentalCoroutinesApi
class CookbookFragmentViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: CookbookFragmentViewModel

    @Before
    fun init() {
        Dispatchers.setMain(dispatcher)
        viewModel = CookbookFragmentViewModel(RecipeRepository(mockRecipes))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `gets default recipe list`() = runTest(dispatcher) {
        val expected = mockRecipes.toRecipeListItems()

        val actual = viewModel.recipeList.first()

        assertEquals(expected, actual)
    }

    @Test
    fun `filters by title`() = runTest(dispatcher) {
        val expected = listOf(
            mockRecipes.toRecipeListItems(),
            mockRecipes.filter { it.title.contains("2") }.toRecipeListItems()
        )

        val actual = async {
            viewModel.recipeList.drop(1).take(2).toList()
        }
        viewModel.setSearchQuery("2")

        assertEquals(expected, actual.await())
    }

    @Test
    fun `toggles category`() = runTest(dispatcher) {
        val expected = listOf(
            mockRecipes.toRecipeListItems(),
            mockRecipes.filter { mockRecipes[0].category == it.category }.toRecipeListItems(),
            mockRecipes.toRecipeListItems()
        )

        val actual = async {
            viewModel.recipeList.drop(1).take(3).toList()
        }
        viewModel.toggleCategory(mockRecipes[0].category)
        viewModel.toggleCategory(mockRecipes[0].category)

        assertEquals(expected, actual.await())
    }

    @Test
    fun `updates filter state`() = runTest(dispatcher) {
        val expected = listOf(
            FilterState(
                query = "",
                categories = listOf(
                    RecipeCategory("Category 1") to false,
                    RecipeCategory("Category 2") to false,
                    RecipeCategory("Category 3") to false
                )
            ),
            FilterState(
                query = "2",
                categories = listOf(
                    RecipeCategory("Category 1") to false,
                    RecipeCategory("Category 2") to false,
                    RecipeCategory("Category 3") to false
                )
            ),
            FilterState(
                query = "2",
                categories = listOf(
                    RecipeCategory("Category 1") to true,
                    RecipeCategory("Category 2") to false,
                    RecipeCategory("Category 3") to false
                )
            )
        )

        val actual = async {
            viewModel.filter.drop(1).take(3).toList()
        }
        viewModel.setSearchQuery("2")
        viewModel.toggleCategory(RecipeCategory("Category 1"))

        assertEquals(expected, actual.await())
    }
}