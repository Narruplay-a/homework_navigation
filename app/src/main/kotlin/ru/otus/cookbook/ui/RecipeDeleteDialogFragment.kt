package ru.otus.cookbook.ui

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import ru.otus.cookbook.R

class RecipeDeleteDialogFragment : DialogFragment() {
    private val title get() = RecipeDeleteDialogFragmentArgs.fromBundle(requireArguments()).title

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        requireContext().let {
            AlertDialog.Builder(it, R.style.RoundedDialog)
                .setTitle(R.string.rdf_title)
                .setMessage(getString(R.string.rdf_message, title))
                .setPositiveButton(R.string.rdf_ok) { _, _ ->
                    findNavController().previousBackStackEntry?.savedStateHandle?.set(RESULT, 1)
                }
                .setNeutralButton(R.string.rdf_cancel) { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
        }

    companion object {
        const val RESULT = "result"
    }
}