package ru.otus.cookbook

import android.app.Application
import ru.otus.cookbook.data.RecipeRepository
import ru.otus.cookbook.data.cookbook

/**
 * The application. Provides access to dependencies.
 */
class App : Application() {
    /**
     * The recipe repository.
     */
    val recipeRepository: RecipeRepository by lazy {
        RecipeRepository(recipes = cookbook)
    }
}