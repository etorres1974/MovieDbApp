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
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
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
    fun write_profile_and_read_in_list() = runBlocking{
        val initialState = profileDao.getById(1)
        assert(initialState.isEmpty())
        val newProfile = Profile(1, "ProfileName")
        profileDao.insert(newProfile)
        val results = profileDao.getById(1)
        assertThat(results.firstOrNull(), equalTo(newProfile))
    }

    @Test
    fun write_watchitem_with_invalid_movie_throws_error() {
        assertThrows(SQLiteConstraintException::class.java){
            runBlocking {
                profileDao.insert(Profile(1,"_"))
                assert(movieDao.getAll().isEmpty())
                watchItemDao.insert(WatchItem(1,1,1))
            }
        }
    }

    @Test
    fun write_watchitem_with_invalid_profile_throws_error() {
        assertThrows(SQLiteConstraintException::class.java){
            runBlocking {
                movieDao.insert(Movie(1,"_"))
                assert(profileDao.getAll().isEmpty())
                watchItemDao.insert(WatchItem(1,1,1))
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun write_watchItem_and_read_in_list() = runBlocking{
        profileDao.insert(Profile(1,"_"))
        movieDao.insert(Movie(1,"_"))
        val initialState = watchItemDao.getAllWatchItens()
        assert(initialState.isEmpty())
        val newWatchItem = WatchItem(1, 1, 1)
        watchItemDao.insert(newWatchItem)
        val results = watchItemDao.getById(1)
        assertThat(results.firstOrNull(), equalTo(newWatchItem))
    }

    @Test
    @Throws(Exception::class)
    fun add_watch_item_to_profile_and_return_in_list() = runBlocking{
        val newProfile = Profile(1,"_")
        profileDao.insert(newProfile)
        movieDao.insert(Movie(1,"_"))
        val newWatchItem = WatchItem(1, 1, 1)
        watchItemDao.insert(newWatchItem)
        val results = profileDao.getFullProfileById(1)
        assert(results.isNotEmpty())
        val fullProfile = results.firstOrNull()
        assertThat(fullProfile?.profile, equalTo(newProfile))
        assertThat(fullProfile?.watchList, equalTo(listOf(newWatchItem)))
    }
}