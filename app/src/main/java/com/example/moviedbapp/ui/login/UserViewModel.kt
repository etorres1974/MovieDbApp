package com.example.moviedbapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedbapp.domain.User
import com.example.moviedbapp.domain.UserRepository
import com.example.moviedbapp.ui.application.launchCatching
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val combineFlow = combine(userRepository.currentUser){users ->
        LoginUiState(
            hasUser = userRepository.hasUser,
            currentUser = users.firstOrNull(),
            onLoginClick = ::onLoginClick,
            onLogoutClick = ::onLogoutClick,
            onCreateAccountClick = ::onCreateAccountClick
        )
    }

    val uiStateStateFlow: StateFlow<LoginUiState> = combineFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = LoginUiState()
        )

    private fun onLoginClick(email : String, pass : String, onSuccess : () -> Unit){
        launchCatching {
            userRepository.authenticate(email, pass)
            onSuccess()
        }
    }

    private fun onLogoutClick(onSuccess: () -> Unit){
        launchCatching {
            userRepository.logout()
            onSuccess()
        }
    }

    private fun onCreateAccountClick(email: String, pass: String, onSuccess: () -> Unit){
        launchCatching {
            userRepository.createAccount(email,pass)
            onSuccess()
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

class LoginUiState(
    val hasUser : Boolean = false,
    val currentUser : User? = null,
    val onCreateAccountClick : (String, String, onSuccess : () -> Unit) -> Unit = {_,_,_ ->},
    val onLoginClick : (String, String,  onSuccess : () -> Unit) -> Unit = { _, _, _ ->},
    val onLogoutClick : (onSuccess : () -> Unit) -> Unit = {}
) {
}