package com.example.moviedbapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedbapp.domain.Movie
import com.example.moviedbapp.domain.MovieRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MovieViewModel(movieRepository: MovieRepository) : ViewModel() {

    val uiStateFlow = movieRepository.getDiscoverMovies().map {
      MovieUiState(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = MovieUiState()
    )
    companion object{
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class MovieUiState(
    val movies : List<Movie> = emptyList()
)