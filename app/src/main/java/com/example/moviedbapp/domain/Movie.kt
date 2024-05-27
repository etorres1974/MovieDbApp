package com.example.moviedbapp.domain

data class Movie(
    val id: Int,
    val adult: Boolean? = null,
    val title: String? = "",
    val poster : String? = "",
    val overview : String? = ""
)
