package com.example.moviedbapp.ui.application

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.moviedbapp.ui.navigation.NavigationDestination
import com.example.moviedbapp.ui.navigation.NavigationDestination.HomeDestination
import com.example.moviedbapp.ui.navigation.NavigationDestination.LoginDestination
import com.example.moviedbapp.ui.navigation.NavigationDestination.NotFoundDestination
import com.example.moviedbapp.ui.navigation.NavigationDestination.ProfileSelectDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    hostNavController: NavHostController,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = TopAppBarDefaults.enterAlwaysScrollBehavior()
) {
    val navBackStackEntry by hostNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentDestination =  currentRoute.toDestination()
    val toolBarName = stringResource(id = currentDestination.titleRes)
    val onBackClick = { currentDestination.backBehaviour(hostNavController) }
    TopAppBarContent(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        title =  toolBarName,
        canNavigateBack = currentDestination.canNavigateBack,
        onBackClick = onBackClick
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun TopAppBarPreview(modifier: Modifier = Modifier, scrollBehavior: TopAppBarScrollBehavior? = null, title : String = "Title", canNavigateBack : Boolean = true, onBackClick : () -> Unit = {}) {
    TopAppBarContent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarContent(modifier: Modifier = Modifier, scrollBehavior: TopAppBarScrollBehavior? = null, title : String = "Title", canNavigateBack : Boolean = true, onBackClick : () -> Unit = {}){
    CenterAlignedTopAppBar(title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = { onBackClick() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back button"
                    )
                }
            }
        })
}

private fun String?.toDestination() : NavigationDestination{
    return when(this){
        LoginDestination.route -> LoginDestination
        ProfileSelectDestination.route -> ProfileSelectDestination
        HomeDestination.route -> HomeDestination
        else -> NotFoundDestination
    }
}

private fun NavigationDestination.backBehaviour(navHostController : NavHostController?)  {
    when(this){
        else -> { navHostController?.navigateUp() }
    }
}