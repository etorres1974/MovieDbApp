package com.example.moviedbapp.ui.application

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.moviedbapp.ui.application.SnackbarMessage.Companion.toMessage
import com.example.moviedbapp.ui.home.HomeScreenPreview
import com.example.moviedbapp.ui.login.PreviewLoginScreen
import com.example.moviedbapp.ui.navigation.AppNavHost
import com.example.moviedbapp.ui.profile.ProfileSelectScreen
import com.example.moviedbapp.ui.profile.ProfileSelectScreenPreview
import com.example.moviedbapp.ui.theme.MovieDbAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val appState = rememberAppState()
            AppContentHolder(appState){  innerPadding ->
                AppNavHost(
                    navHostController = appState.hostNavController,
                    modifier = Modifier.padding(innerPadding))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContentHolder(appState: AppState? = null, content : @Composable (PaddingValues) -> Unit){
    MovieDbAppTheme {
        Scaffold(
            snackbarHost = {
                appState?.let {
                    SnackbarHost(
                        hostState = appState.snackbarHostState,
                        modifier = Modifier.padding(8.dp),
                        snackbar = { snackbarData ->
                            Snackbar(snackbarData, contentColor = MaterialTheme.colorScheme.onPrimary)
                        }
                    )
                }
            },
            modifier = Modifier.fillMaxSize(),
            topBar = {
                appState?.hostNavController?.let{
                    TopAppBar(hostNavController = appState.hostNavController)
                }
            },
        ) { innerPadding ->
           content(innerPadding)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppLoginPreview() {
    AppContentHolder { innerPadding ->
        PreviewLoginScreen(modifier = Modifier.padding(innerPadding))
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    AppContentHolder { innerPadding ->
        ProfileSelectScreenPreview(modifier = Modifier.padding(innerPadding))
    }
}

@Preview(showBackground = true)
@Composable
fun AppHomePreview(){
    AppContentHolder { innerPadding ->
        HomeScreenPreview(modifier = Modifier.padding(innerPadding))
    }
}