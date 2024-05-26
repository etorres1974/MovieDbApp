package com.example.moviedbapp.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviedbapp.domain.Movie
import com.example.moviedbapp.ui.application.AppViewModelProvider
import com.example.moviedbapp.ui.movie.MovieList
import com.example.moviedbapp.ui.profile.ProfileViewModel

interface HomeNavigator{
    fun selectProfileScreen()
}
@Composable
fun HomeScreen(navigator : HomeNavigator, modifier: Modifier = Modifier, viewModel: MovieViewModel = viewModel(factory = AppViewModelProvider.Factory)){
    val moviesUiState by viewModel.uiStateFlow.collectAsState()
    HomeScreenContent(navigator = navigator, modifier = modifier, moviesUiState)

}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview(modifier: Modifier = Modifier){
    val movies = listOf(
        Movie(1, title = "Aaaa"),
        Movie(2, title = "Bbbbb"),
        Movie(3, title = "Cccc")
    )
    val uiState = MovieUiState(
        movies = movies
    )
    HomeScreenContent(navigator = null, modifier = modifier, uiState)
}
@Composable
fun HomeScreenContent(navigator : HomeNavigator?, modifier: Modifier = Modifier,  moviesUiState: MovieUiState){
    Column(modifier) {
        Text(text = "Home Screen")
        MovieList(movies = moviesUiState.movies)
    }
}