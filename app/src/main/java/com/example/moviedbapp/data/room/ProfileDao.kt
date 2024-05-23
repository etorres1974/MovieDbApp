package com.example.moviedbapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.moviedbapp.domain.Profile
@Dao
abstract class ProfileDao : BaseDao<Profile>{

    @Query("Select * FROM profile")
    abstract fun getAllProfiles() : List<Profile>
    @Query("Select * FROM profile WHERE id = :id")
    abstract fun getProfileById(id : Int) : List<Profile>
}