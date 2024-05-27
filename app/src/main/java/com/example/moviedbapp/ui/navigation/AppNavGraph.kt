package com.example.moviedbapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moviedbapp.ui.application.AppViewModelProvider
import com.example.moviedbapp.ui.home.HomeNavigator
import com.example.moviedbapp.ui.home.HomeScreen
import com.example.moviedbapp.ui.login.LoginNavigator
import com.example.moviedbapp.ui.login.LoginScreen
import com.example.moviedbapp.ui.login.UserViewModel
import com.example.moviedbapp.ui.profile.ProfileSelectNavigator
import com.example.moviedbapp.ui.profile.ProfileSelectScreen
import com.example.moviedbapp.ui.navigation.NavigationDestination.*
@Composable
fun AppNavHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navHostController,
        startDestination = LoginDestination.route,
        modifier = modifier
    ){
        composable(route = LoginDestination.route){
            LoginScreen(LoginNavImp(navHostController))
        }
        composable(route = ProfileSelectDestination.route){
            ProfileSelectScreen(ProfileSelectNavImp(navHostController))
        }
        composable(route = HomeDestination.route){
            HomeScreen(navigator = HomeNavImp(navHostController))
        }
    }
}

class LoginNavImp(private val navHostController: NavHostController) : LoginNavigator {
    override fun toProfiles() {
        navHostController.navigate(ProfileSelectDestination.route)
    }
}

class ProfileSelectNavImp(private val navHostController: NavHostController) : ProfileSelectNavigator{
    override fun logout() {
        navHostController.navigate(LoginDestination.route)
    }

    override fun home() {
        navHostController.navigate(HomeDestination.route)
    }

}

class HomeNavImp(private val navHostController: NavHostController) : HomeNavigator{
    override fun selectProfileScreen() {
        navHostController.navigate(ProfileSelectDestination.route)
    }
}