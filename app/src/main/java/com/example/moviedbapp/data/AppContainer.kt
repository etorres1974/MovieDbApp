package com.example.moviedbapp.data

import android.content.Context
import com.example.moviedbapp.data.impRepo.ProfileRepoImplementation
import com.example.moviedbapp.data.room.AppDatabase

interface AppContainer {
    val profileRepository : ProfileRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    val db = AppDatabase.getInstance(context)
    override val profileRepository : ProfileRepository by lazy {
        ProfileRepoImplementation(db.watchItemDao(), db.profileDao())
    }
}