package com.example.moviedbapp.data

import com.example.moviedbapp.data.room.Movie
import com.example.moviedbapp.data.room.Profile
import com.example.moviedbapp.data.room.ProfileAndWatchList
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    val allProfiles : Flow<List<Profile>>
    val selectedProfile: Flow<List<ProfileAndWatchList>>
    suspend fun addProfile(name : String)
    suspend fun selectProfile(profile: Profile)
    suspend fun addMovieToWatchList(movie : Movie)
}