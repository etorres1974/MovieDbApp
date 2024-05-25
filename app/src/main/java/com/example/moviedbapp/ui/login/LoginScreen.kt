package com.example.moviedbapp.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviedbapp.ui.application.AppViewModelProvider

interface LoginNavigator{
    fun toProfiles()
}
@Composable
fun LoginScreen(navigator : LoginNavigator, modifier: Modifier = Modifier, userViewModel: UserViewModel = viewModel(factory = AppViewModelProvider.Factory)){
    LoginContent(LoginUiState(),  modifier, navigator)
}

@Composable
@Preview(showBackground = true)
fun PreviewLoginScreen(modifier: Modifier = Modifier){
    LoginContent(LoginUiState(), modifier, null)
}

@Composable
fun LoginContent(loginUiState: LoginUiState, modifier: Modifier = Modifier,  navigator : LoginNavigator? = null){
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                loginUiState.onLoginClick(email, password)
                navigator?.toProfiles()
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Login")
        }
    }
}