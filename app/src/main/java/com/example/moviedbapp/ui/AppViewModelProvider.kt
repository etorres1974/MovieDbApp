package com.example.moviedbapp.ui
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.moviedbapp.MovieApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {

        // Initializer for HomeViewModel
        initializer {
            ProfileViewModel(movieApplication().container.profileRepository)
        }
    }
}

fun CreationExtras.movieApplication(): MovieApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as MovieApplication)
