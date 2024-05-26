package com.example.moviedbapp.data.impRepo

import com.example.moviedbapp.domain.User
import com.example.moviedbapp.domain.UserRepository
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseUserRepoImp(private val auth: FirebaseAuth) : UserRepository {

    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override val hasUser: Boolean
        get() = auth.currentUser != null

    override val currentUser: Flow<User>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let { User(it.uid, it.isAnonymous) } ?: User())
                }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }

    override suspend fun authenticate(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }
    override suspend fun createAccount(email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email,pass).await()
    }
    override suspend fun logout() {
        auth.signOut()
    }

    companion object {
        private const val LINK_ACCOUNT_TRACE = "linkAccount"
    }
}