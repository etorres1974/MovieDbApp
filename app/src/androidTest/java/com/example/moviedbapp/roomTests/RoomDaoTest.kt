package com.example.moviedbapp.roomTests

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.moviedbapp.data.room.AppDatabase
import com.example.moviedbapp.data.room.MovieDao
import com.example.moviedbapp.data.room.ProfileDao
import com.example.moviedbapp.data.room.WatchItemDao
import com.example.moviedbapp.data.room.Movie
import com.example.moviedbapp.data.room.Profile
import com.example.moviedbapp.data.room.WatchItem
import com.example.moviedbapp.data.room.selectProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.UUID
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class RoomDaoTest {
    private lateinit var profileDao : ProfileDao
    private lateinit var watchItemDao: WatchItemDao
    private lateinit var movieDao : MovieDao
    private lateinit var db : AppDatabase

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()
        profileDao = db.profileDao()
        movieDao = db.movieDao()
        watchItemDao = db.watchItemDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun write_profile_and_read_in_list() = runTest{
        val initialState = profileDao.getAll().first()
        assert(initialState.isEmpty())

        val newProfile = Profile("ProfileName")
        profileDao.insert(newProfile)

        val results = profileDao.getById(newProfile.id)
        assertThat(results.firstOrNull()?.id, equalTo(newProfile.id))
    }

    @Test
    fun write_watchitem_with_invalid_movie_throws_error() {
        assertThrows(SQLiteConstraintException::class.java){
            runBlocking {
                val profile = Profile("_")
                val movie = Movie("_")
                val randomMovieId = UUID.randomUUID()
                profileDao.insert(profile)
                movieDao.insert(movie)
                watchItemDao.insert(WatchItem(profile.id, randomMovieId))
            }
        }
    }

    @Test
    fun write_watchitem_with_invalid_profile_throws_error() {
        assertThrows(SQLiteConstraintException::class.java){
            runBlocking{
                val profile = Profile("_")
                val movie = Movie("_")
                val randomProfileId = UUID.randomUUID()
                profileDao.insert(profile)
                movieDao.insert(movie)
                watchItemDao.insert(WatchItem(randomProfileId,movie.id,))
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun write_watchItem_and_read_in_list() = runBlocking{
        var profile = Profile("_")
        var movie = Movie("_")
        profileDao.insert(profile)
        movieDao.insert(movie)
        val initialState = watchItemDao.getAllWatchItens().first()
        assert(initialState.isEmpty())
        val watchItem = (WatchItem(profile.id, movie.id))
        watchItemDao.insert(watchItem)
        val results = watchItemDao.getById(watchItem.id)
        assertThat(results.firstOrNull()?.id, equalTo(watchItem.id))
    }

    @Test
    @Throws(Exception::class)
    fun add_watch_item_to_profile_and_return_in_list() = runTest{
        val newProfile = Profile("_")
        val movie = Movie("_")
        profileDao.insert(newProfile)
        movieDao.insert(movie)
        val newWatchItem = WatchItem(newProfile.id, movie.id)
        watchItemDao.insert(newWatchItem)

        val results = profileDao.getFullProfileById(newProfile.id)
        assert(results.isNotEmpty())
        val fullProfile = results.firstOrNull()
        assertThat(fullProfile?.profile?.id, equalTo(newProfile.id))
        assertThat(fullProfile?.watchList?.first()?.id, equalTo(newWatchItem.id))
    }

    @Test
    @Throws(Exception::class)
    fun select_profile_switch_all_profiles() = runTest {
        val p1 = Profile( "Profile_1")
        val p2 = Profile("Profile_2")
        val p3 = Profile("Profile_3")
        profileDao.insert(p1,p2,p3)
        assert(profileDao.getAll().first().size == 3)
        assert(profileDao.getSelected().isEmpty())

        profileDao.selectProfile(p1)
        assertThat(profileDao.getSelected().firstOrNull()?.id, equalTo(p1.id))

        profileDao.selectProfile(p2)
        assertThat(profileDao.getSelected().firstOrNull()?.id, equalTo(p2.id))
    }
}