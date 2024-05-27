package com.example.moviedbapp.domain

import com.example.moviedbapp.data.room.ProfileAndWatchList
import kotlinx.coroutines.flow.Flow


interface MovieRepository {
    fun getDiscoverMovies() : Flow<List<Movie>>
    suspend fun addMovieToWishList(isFavorite : Boolean, movie: Movie)
}