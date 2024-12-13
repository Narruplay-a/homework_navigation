package ru.otus.webinar.ui

interface DialogListener {
    fun onDialogConfirm(tag: String) = Unit
    fun onDialogDismiss(tag: String) = Unit
}