package com.example.moviedbapp.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "profile")
data class Profile(
    @PrimaryKey val id : Int,
    val name : String,
    //val watchList : List<Int>
)
