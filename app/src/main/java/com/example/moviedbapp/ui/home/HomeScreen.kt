package com.example.moviedbapp.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

interface HomeNavigator{
    fun selectProfileScreen()
}
@Composable
fun HomeScreen(navigator : HomeNavigator, modifier: Modifier = Modifier){
    HomeScreenContent(navigator = navigator, modifier = modifier)
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview(modifier: Modifier = Modifier){
    HomeScreenContent(navigator = null, modifier = modifier)
}
@Composable
fun HomeScreenContent(navigator : HomeNavigator?, modifier: Modifier = Modifier){
    Column(modifier) {
        Text(text = "Home Screen")
    }
}