package com.example.moviedbapp.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviedbapp.ui.application.AppViewModelProvider

interface LoginNavigator {
    fun toProfiles()
}

@Composable
fun LoginScreen(
    navigator: LoginNavigator,
    modifier: Modifier = Modifier,
    viewModel: UserViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val loginUiState by viewModel.uiStateStateFlow.collectAsState()
    LoginContent(loginUiState, modifier, navigator)
}

@Composable
@Preview(showBackground = true)
fun PreviewLoginScreen(modifier: Modifier = Modifier) {
    LoginContent(LoginUiState(), modifier, null)
}

@Composable
fun LoginContent(
    loginUiState: LoginUiState,
    modifier: Modifier = Modifier,
    navigator: LoginNavigator? = null
) {

    if (loginUiState.hasUser) {
        navigator?.toProfiles()
    }
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        OutlinedTextField(
            value = loginUiState.inputState.value.email,
            onValueChange = { loginUiState.actions?.onEmailChanged(it) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = loginUiState.inputState.value.pass,
            onValueChange = { loginUiState.actions?.onPassChanged(it) },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Row {
            Button(
                onClick = {
                    loginUiState.actions?.onCreateAccountClick{
                        navigator?.toProfiles()
                    }
                },
                modifier = modifier
            ) {
                Text(text = "Create Account")
            }
            Spacer(modifier = modifier.weight(1f))
            Button(
                onClick = {
                    loginUiState.actions?.onLoginClick {
                        navigator?.toProfiles()
                    }
                },
                modifier = modifier
            ) {
                Text(text = "Login")
            }
        }

    }
}