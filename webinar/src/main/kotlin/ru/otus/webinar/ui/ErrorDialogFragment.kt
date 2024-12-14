package ru.otus.webinar.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController

class ErrorDialogFragment : DialogFragment() {
    companion object {
        const val CONFIRMATION_RESULT = "confirmation_result"
    }

    private val message get() = ErrorDialogFragmentArgs.fromBundle(requireArguments()).message

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setPositiveButton(getString(android.R.string.ok)) { _, _ ->
                dismiss()
                setResult(true)
            }
            .setNegativeButton(getString(android.R.string.cancel)) { _, _ ->
                dismiss()
                setResult(false)
            }
            .create()

    private fun setResult(result: Boolean) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(
            CONFIRMATION_RESULT,
            result
        )
        findNavController().popBackStack()
    }
}