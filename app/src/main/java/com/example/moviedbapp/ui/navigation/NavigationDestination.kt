package com.example.moviedbapp.ui.navigation

import com.example.moviedbapp.R

/**
 * Interface to describe the navigation destinations for the app
 */
sealed class NavigationDestination(
    val route: String,
    val titleRes: Int,
    val canNavigateBack : Boolean = true
)  {
    data object NotFoundDestination : NavigationDestination("not_found", R.string.notFound)
    data object LoginDestination : NavigationDestination("login", R.string.loginTitle, false)
    data object ProfileSelectDestination : NavigationDestination("profile_select", R.string.profileSelectTitle)
    data object HomeDestination : NavigationDestination("home", R.string.homeTitle)
}