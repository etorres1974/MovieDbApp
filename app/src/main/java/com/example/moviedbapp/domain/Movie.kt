package com.example.moviedbapp.domain

data class Movie(
    val id: Int,
    val adult: Boolean? = null,
    val backdrop_path: String? = null,
    val original_title: String? = null,
    val poster_path: String? = null,
    val title: String? = "",
)