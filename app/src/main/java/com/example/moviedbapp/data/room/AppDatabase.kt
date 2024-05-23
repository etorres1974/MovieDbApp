package com.example.moviedbapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Profile::class, WatchItem::class, Movie::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao() : ProfileDao
    abstract fun watchItemDao() : WatchItemDao
    abstract fun movieDao() : MovieDao
}

fun getDb(appContext : Context) = Room.databaseBuilder(appContext, AppDatabase::class.java, "app-database" ).build()
