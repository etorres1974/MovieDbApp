package com.example.moviedbapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviedbapp.domain.Profile

@Database(entities = [Profile::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao() : ProfileDao
}

fun getDb(appContext : Context) = Room.databaseBuilder(appContext, AppDatabase::class.java, "app-database" ).build()
