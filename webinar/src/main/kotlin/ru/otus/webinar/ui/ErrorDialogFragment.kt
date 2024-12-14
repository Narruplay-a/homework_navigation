package ru.otus.webinar.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ErrorDialogFragment : DialogFragment() {
    companion object {
        const val TAG = "ErrorDialog"
    }

    private val message get() = ErrorDialogFragmentArgs.fromBundle(requireArguments()).message

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage(message)
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