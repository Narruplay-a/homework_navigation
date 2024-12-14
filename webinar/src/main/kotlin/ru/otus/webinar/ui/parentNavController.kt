package ru.otus.webinar.ui

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

/**
 * Get the parent NavController of the nested navigation.
 */
fun Fragment.parentNavController(): NavController {
    return requireParentFragment().requireParentFragment().findNavController()
}