package com.example.moviedbapp.ui.login

import androidx.lifecycle.ViewModel
import com.example.moviedbapp.domain.UserRepository

class UserViewModel(userRepository: UserRepository) : ViewModel() {

}

class LoginUiState(
    val onLoginClick : (String, String) -> Unit = { _, _ ->},
)