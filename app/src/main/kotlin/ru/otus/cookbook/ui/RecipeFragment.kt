package ru.otus.cookbook.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ru.otus.cookbook.data.Recipe
import ru.otus.cookbook.databinding.FragmentRecipeBinding
import coil.load
import ru.otus.cookbook.R

class RecipeFragment : Fragment() {
    private val recipeId: Int get() = RecipeFragmentArgs.fromBundle(requireArguments()).recipeID

    private val binding = FragmentBindingDelegate<FragmentRecipeBinding>(this)
    private val model: RecipeFragmentViewModel by viewModels(
        extrasProducer = {
            MutableCreationExtras(defaultViewModelCreationExtras).apply {
                set(RecipeFragmentViewModel.RECIPE_ID_KEY, recipeId)
            }
        },
        factoryProducer = { RecipeFragmentViewModel.Factory }
    )
    private val navigationController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.bind(
        container,
        FragmentRecipeBinding::inflate
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            model.recipe
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect(::displayRecipe)
        }

        displayRecipe(model.recipe.value)

        navigationController.currentBackStackEntry?.savedStateHandle?.getLiveData<Int>(
            RecipeDeleteDialogFragment.RESULT)
            ?.observe(viewLifecycleOwner) {
                if (it == 1) {
                    deleteRecipe()
                }
            }
    }

    private fun getTitle(): String {
        return model.recipe.value.title
    }

    private fun displayRecipe(recipe: Recipe) {
        binding.withBinding {
            twName.text = recipe.title
            twDescription.text = recipe.description
            imageView.load(recipe.imageUrl) {
                crossfade(true)
            }
            button.setOnClickListener {
                navigationController.navigate(RecipeFragmentDirections.actionRecipeToDialog(
                    getTitle()))
            }
        }
    }

    private fun deleteRecipe() {
        model.delete()
        findNavController().popBackStack(R.id.fragment_cookbook, false)
    }
}