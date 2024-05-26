package com.example.moviedbapp.data

import android.content.Context
import com.example.moviedbapp.data.impRepo.FirebaseUserRepoImp
import com.example.moviedbapp.data.impRepo.ProfileRepoImplementation
import com.example.moviedbapp.data.room.AppDatabase
import com.example.moviedbapp.domain.ProfileRepository
import com.example.moviedbapp.domain.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

interface AppContainer {
    val profileRepository : ProfileRepository
    val userRepository : UserRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    private val db = AppDatabase.getInstance(context)
    override val profileRepository : ProfileRepository by lazy {
        ProfileRepoImplementation(db.watchItemDao(), db.profileDao())
    }
    override val userRepository: UserRepository by lazy {
        FirebaseUserRepoImp(Firebase.auth)
    }
}