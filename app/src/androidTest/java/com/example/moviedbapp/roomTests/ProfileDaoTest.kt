package com.example.moviedbapp.roomTests

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.moviedbapp.data.room.AppDatabase
import com.example.moviedbapp.data.room.ProfileDao
import com.example.moviedbapp.domain.Profile
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class ProfileDaoTest {
    private lateinit var profileDao : ProfileDao
    private lateinit var db : AppDatabase

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()
        profileDao = db.profileDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun write_profile_and_read_in_list() = runBlocking{
        val initialState = profileDao.getProfileById(1)
        assert(initialState.isEmpty())

        val newProfile = Profile(1, "ProfileName")
        profileDao.insert(newProfile)
        val results = profileDao.getProfileById(1)
        assertThat(results.firstOrNull(), equalTo(newProfile))
    }
}