package com.example.moviedbapp.domain

import kotlinx.coroutines.flow.Flow


interface MovieRepository {
    fun getDiscoverMovies() : Flow<List<Movie>>
}