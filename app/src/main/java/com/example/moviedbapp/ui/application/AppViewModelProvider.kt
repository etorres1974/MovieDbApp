package com.example.moviedbapp.ui.application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.moviedbapp.MovieApplication
import com.example.moviedbapp.ui.login.UserViewModel
import com.example.moviedbapp.ui.profile.ProfileViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {

        // Initializer for HomeViewModel
        initializer {
            ProfileViewModel(movieApplication().container.profileRepository, movieApplication().container.userRepository)
        }
        initializer {
            UserViewModel(movieApplication().container.userRepository)
        }
    }
}

fun CreationExtras.movieApplication(): MovieApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as MovieApplication)
