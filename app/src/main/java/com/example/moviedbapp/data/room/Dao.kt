package com.example.moviedbapp.data.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
abstract class ProfileDao : BaseDao<Profile>{

    @Query("Select * FROM profile")
    abstract fun getAll() : List<Profile>
    @Query("Select * FROM profile WHERE id = :id")
    abstract fun getById(id : Int) : List<Profile>
    @Transaction
    @Query("Select * FROM profile WHERE id = :id")
    abstract fun getFullProfileById(id : Int) : List<ProfileAndWatchList>
}

@Dao
abstract class WatchItemDao : BaseDao<WatchItem>{
    @Query("SELECT * FROM  watchItem")
    abstract fun getAllWatchItens() : List<WatchItem>

    @Query("Select * FROM watchItem WHERE id = :id")
    abstract fun getById(id : Int) : List<WatchItem>
}

@Dao
abstract class MovieDao : BaseDao<Movie>{
    @Query("SELECT * FROM movie")
    abstract fun getAll() : List<Movie>
}