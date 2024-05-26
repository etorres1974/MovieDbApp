package com.example.moviedbapp

import com.example.moviedbapp.data.network.MovieDbApiService
import com.example.moviedbapp.data.network.getRetrofitInstance
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RetrofitTest {
    private val api = getRetrofitInstance()
    private lateinit var apiMovieDbApiService: MovieDbApiService
    @Before
    fun setup(){
        apiMovieDbApiService = api.create(MovieDbApiService::class.java)
    }
    @Test
    fun api_discover_movies_is_not_empty() {
        runBlocking{
            val res = apiMovieDbApiService.getDiscoverMovies()
            println(res)
            assert(res.results?.isNotEmpty() == true)
        }
    }
}