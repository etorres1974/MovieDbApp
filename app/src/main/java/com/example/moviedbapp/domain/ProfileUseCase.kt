package com.example.moviedbapp.domain

interface ProfileUseCase {
    fun addProfile(name : String)
    fun selectProfile(id : Int)
    fun addMovieIdToWatchList(id : Int)
}