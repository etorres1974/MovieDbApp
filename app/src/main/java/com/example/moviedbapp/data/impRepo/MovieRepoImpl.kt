package com.example.moviedbapp.data.impRepo

import com.example.moviedbapp.data.network.MovieDbApiService
import com.example.moviedbapp.data.network.Result
import com.example.moviedbapp.domain.Movie
import com.example.moviedbapp.domain.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepoImpl(val movieDbApiService: MovieDbApiService) : MovieRepository {
    override fun getDiscoverMovies() = flow {
        emit(movieDbApiService.getDiscoverMovies().results?.mapNotNull { it?.toMovie() } ?: emptyList())
    }
}

fun Result.toMovie() = Movie(
    id = id ?: 0,
    title = title
)