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
            AlertDialog.Builder(it)
                .setTitle(title)
                .setMessage(R.string.rdf_message)
                .setPositiveButton(R.string.rdf_ok) { dialog, button ->
                    findNavController().previousBackStackEntry?.savedStateHandle?.set(RESULT, 1)
                }
                .setNegativeButton(R.string.rdf_no) { dialog, button ->
                    findNavController().previousBackStackEntry?.savedStateHandle?.set(RESULT, 0)
                }
                .setNeutralButton(R.string.rdf_cancel) { dialog, button ->
                    dialog.dismiss()
                }
                .create()
        }

    companion object {
        const val RESULT = "result"
    }
}