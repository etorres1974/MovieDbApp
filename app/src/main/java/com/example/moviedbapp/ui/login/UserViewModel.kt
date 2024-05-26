package com.example.moviedbapp.ui.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedbapp.domain.User
import com.example.moviedbapp.domain.UserRepository
import com.example.moviedbapp.ui.application.launchCatching
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class UserViewModel(private val userRepository: UserRepository) : ViewModel(), LoginScreenActions {

    private val inputState = mutableStateOf(LoginInputState())

    override fun onEmailChanged(newValue: String) {
        inputState.value = inputState.value.copy(
            email = newValue
        )
    }

    override fun onPassChanged(newValue: String) {
        inputState.value = inputState.value.copy(
            pass = newValue
        )
    }

    private val combineFlow = combine(userRepository.currentUser){users ->
        LoginUiState(
            inputState = inputState,
            hasUser = userRepository.hasUser,
            actions = this
        )
    }

    val uiStateStateFlow: StateFlow<LoginUiState> = combineFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = LoginUiState(actions = this)
        )

    override fun onLoginClick(onSuccess : () -> Unit){
        launchCatching {
            val email = inputState.value.email
            val pass = inputState.value.pass
            userRepository.authenticate(email, pass)
            onSuccess()
        }
    }



    override fun onCreateAccountClick(onSuccess: () -> Unit){
        launchCatching {
            val email = inputState.value.email
            val pass = inputState.value.pass
            userRepository.createAccount(email,pass)
            onSuccess()
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class LoginInputState(
    val email: String = "",
    val pass : String = ""
)
class LoginUiState(
    val inputState: MutableState<LoginInputState> = mutableStateOf(LoginInputState()),
    val hasUser : Boolean = false,
    val actions: LoginScreenActions? = null
)

interface LoginScreenActions{
    fun onEmailChanged(newValue : String)
    fun onPassChanged(newValue : String)
    fun onCreateAccountClick(onSuccess : () -> Unit)
    fun onLoginClick(onSuccess : () -> Unit)
}