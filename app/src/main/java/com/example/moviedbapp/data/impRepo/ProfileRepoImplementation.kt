package com.example.moviedbapp.data.impRepo

import com.example.moviedbapp.data.ProfileRepository
import com.example.moviedbapp.data.room.Movie
import com.example.moviedbapp.data.room.Profile
import com.example.moviedbapp.data.room.ProfileDao
import com.example.moviedbapp.data.room.WatchItem
import com.example.moviedbapp.data.room.WatchItemDao
import com.example.moviedbapp.data.room.selectProfile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProfileRepoImplementation(
    private val watchItemDao: WatchItemDao,
    private val profileDao : ProfileDao,
    private val dispatcher : CoroutineDispatcher = Dispatchers.Default
) : ProfileRepository {

    override val allProfiles = profileDao.getAll()
    override val selectedProfile = profileDao.getSelectedFullProfile()
    override suspend  fun addProfile(name: String) {
        withContext(dispatcher){
            profileDao.insert(Profile(name))
        }
    }

    override suspend fun selectProfile(profile: Profile) {
        withContext(dispatcher){
            profileDao.selectProfile(profile)
        }
    }

    override suspend fun addMovieToWatchList(movie : Movie) {
        withContext(dispatcher) {
            val profileId = profileDao.getSelected().first().id
            watchItemDao.insert(WatchItem(profileId, movie.id))
        }
    }

    override suspend fun deleteProfile(profile: Profile) {
        withContext(dispatcher){
            profileDao.delete(profile)
        }
    }
}