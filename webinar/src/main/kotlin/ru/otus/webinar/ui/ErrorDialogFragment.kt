package ru.otus.webinar.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ErrorDialogFragment : DialogFragment() {
    companion object {
        private const val ERROR = "error"
        const val TAG = "ErrorDialog"

        fun newInstance(error: String): ErrorDialogFragment = ErrorDialogFragment().apply {
            arguments = Bundle().apply {
                putString(ERROR, error)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage(arguments?.getString(ERROR))
            .setPositiveButton(getString(android.R.string.ok)) { _, _ ->
                dismiss()
                requireDialogListener { onDialogConfirm(tag.orEmpty()) }
            }
            .setNegativeButton(getString(android.R.string.cancel)) { _, _ ->
                dismiss()
                requireDialogListener { onDialogDismiss(tag.orEmpty()) }
            }
            .create()


    private inline fun requireDialogListener(block: DialogListener.() -> Unit) {
        (requireParentFragment() as DialogListener).block()
    }
}