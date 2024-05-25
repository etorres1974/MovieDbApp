package com.example.moviedbapp.roomTests

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.moviedbapp.data.impRepo.ProfileRepoImplementation
import com.example.moviedbapp.domain.ProfileRepository
import com.example.moviedbapp.data.room.AppDatabase
import com.example.moviedbapp.data.room.Movie
import com.example.moviedbapp.data.room.MovieDao
import com.example.moviedbapp.data.room.ProfileDao
import com.example.moviedbapp.data.room.WatchItemDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class RepositoryDaoTest {
    private lateinit var profileDao : ProfileDao
    private lateinit var watchItemDao: WatchItemDao
    private lateinit var movieDao : MovieDao
    private lateinit var db : AppDatabase
    private lateinit var profileRepository: ProfileRepository

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()
        profileDao = db.profileDao()
        movieDao = db.movieDao()
        watchItemDao = db.watchItemDao()
        profileRepository = ProfileRepoImplementation(watchItemDao, profileDao)
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun profile_repository_flow_updates_on_db_insert() = runTest {
        val flow = profileRepository.allProfiles
        assert(flow.first().isEmpty())
        profileRepository.addProfile("profile_name")
        assert(flow.first().isNotEmpty())
    }
    @Test
    @Throws(Exception::class)
    fun select_profile_updates_flow() = runTest {
        val flow = profileRepository.selectedProfile
        assert(flow.first().isEmpty())
        profileRepository.addProfile("profile_name")
        val profile = profileRepository.allProfiles.first().first()
        profileRepository.selectProfile(profile)
        assert(flow.first().isNotEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun watchList_flow_update() = runTest{
        profileRepository.addProfile("profile_name")
        val profile = profileRepository.allProfiles.first().first()
        profileRepository.selectProfile(profile)

        assert(profileRepository.selectedProfile.first().first().watchList.isEmpty())
        val movie = Movie("_")
        movieDao.insert(movie)
        profileRepository.addMovieToWatchList(movie)
        assert(profileRepository.selectedProfile.first().first().watchList.isNotEmpty())
    }

}