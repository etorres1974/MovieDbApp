package com.example.moviedbapp.ui.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedbapp.data.room.ProfileAndWatchList
import com.example.moviedbapp.domain.Movie
import com.example.moviedbapp.domain.MovieRepository
import com.example.moviedbapp.ui.application.launchCatching
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel(), MovieUiActions {

    private val movieListMode = mutableStateOf(MovieListMode.Discover)


    private val combineFlow = movieRepository.getDiscoverMovies().map { discover ->
        MovieUiState(
            mode = movieListMode,
            wishlistMovies = emptyList(),
            discoverMovies = discover,
            actions = this
        )
    }
    val uiStateFlow = combineFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = MovieUiState(actions = this)
    )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    override fun addToWishList(isFavorite: Boolean, movie: Movie) {
        launchCatching {
            movieRepository.addMovieToWishList(isFavorite, movie)
        }
    }

    override fun toggleMovieListMode() {
        launchCatching {

        }
        when (movieListMode.value) {
            MovieListMode.Discover -> {
                movieListMode.value = MovieListMode.Wishlist
            }

            MovieListMode.Wishlist -> {
                movieListMode.value = MovieListMode.Discover
            }
        }
    }
}

data class MovieUiState(
    val mode: MutableState<MovieListMode> = mutableStateOf(MovieListMode.Discover),
    val discoverMovies: List<Movie> = emptyList(),
    val wishlistMovies: List<Movie> = emptyList(),
    val actions: MovieUiActions? = null
)

enum class MovieListMode {
    Discover,
    Wishlist
}

interface MovieUiActions {
    fun addToWishList(isFavorite: Boolean, movie: Movie)
    fun toggleMovieListMode()
}