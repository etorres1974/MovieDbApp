package com.example.moviedbapp

import android.app.Application
import com.example.moviedbapp.data.AppContainer
import com.example.moviedbapp.data.AppDataContainer

class MovieApplication : Application() {

    lateinit var container : AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}