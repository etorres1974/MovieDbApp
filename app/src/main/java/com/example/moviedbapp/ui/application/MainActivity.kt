package com.example.moviedbapp.ui.application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.moviedbapp.ui.home.HomeScreenPreview
import com.example.moviedbapp.ui.login.PreviewLoginScreen
import com.example.moviedbapp.ui.navigation.AppNavHost
import com.example.moviedbapp.ui.profile.ProfileSelectScreen
import com.example.moviedbapp.ui.profile.ProfileSelectScreenPreview
import com.example.moviedbapp.ui.theme.MovieDbAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val hostNavController = rememberNavController()
            AppContentHolder(hostNavController){  innerPadding ->
                AppNavHost(
                    navHostController =  hostNavController,
                    modifier = Modifier.padding(innerPadding))
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContentHolder(hostNavController: NavHostController? = null, content : @Composable (PaddingValues) -> Unit){
    MovieDbAppTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                hostNavController?.let{
                    TopAppBar(hostNavController = hostNavController)
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