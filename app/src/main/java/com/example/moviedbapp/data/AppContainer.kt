package com.example.moviedbapp.data

import android.content.Context
import com.example.moviedbapp.data.impRepo.LocalUserRepoImplementation
import com.example.moviedbapp.data.impRepo.ProfileRepoImplementation
import com.example.moviedbapp.data.room.AppDatabase
import com.example.moviedbapp.domain.ProfileRepository
import com.example.moviedbapp.domain.UserRepository

interface AppContainer {
    val profileRepository : ProfileRepository
    val userRepository : UserRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    val db = AppDatabase.getInstance(context)
    override val profileRepository : ProfileRepository by lazy {
        ProfileRepoImplementation(db.watchItemDao(), db.profileDao())
    }
    override val userRepository: UserRepository by lazy {
        LocalUserRepoImplementation()
    }
}