package com.example.moviedbapp.domain

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    val currentUserId: String
    val hasUser: Boolean

    val currentUser: Flow<User>

    suspend fun authenticate(email: String, pass: String)
    suspend fun createAccount(email: String, pass: String)
    suspend fun logout()
}