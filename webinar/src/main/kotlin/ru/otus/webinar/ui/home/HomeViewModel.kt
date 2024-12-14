package ru.otus.webinar.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HomeViewModel(string: String) : ViewModel() {

    private val _text = MutableLiveData(string)

    val text: LiveData<String> get() = _text

    class Factory(private val string: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(string) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}