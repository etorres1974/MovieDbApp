package com.example.moviedbapp.data.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID

@Dao
abstract class ProfileDao : BaseDao<Profile>{

    @Query("Select * FROM profile")
    abstract fun getAll() : Flow<List<Profile>>

    @Query("Select * FROM profile where selected = true")
    abstract suspend fun getSelected() : List<Profile>
    @Transaction
    @Query("Select * FROM profile  where selected = true")
    abstract fun getSelectedFullProfile() : Flow<List<ProfileAndWatchList>>

    @Transaction
    @Query("Select * FROM profile  where selected = true")
    abstract fun getSelectedFullProfileList() : List<ProfileAndWatchList>

    @Query("Select * FROM profile WHERE id = :id")
    abstract fun getById(id : UUID) : List<Profile>
    @Transaction
    @Query("Select * FROM profile WHERE id = :id")
    abstract fun getFullProfileById(id : UUID) : List<ProfileAndWatchList>

}

@Transaction
suspend inline fun ProfileDao.selectProfile(profile : Profile) {
    getSelected().firstOrNull()?.let {
        it.selected = false
        update(it)
    }
    profile.selected = true
    update(profile)
}
@Dao
abstract class WatchItemDao : BaseDao<WatchItem>{
    @Query("SELECT * FROM  watchItem")
    abstract fun getAllWatchItens() : Flow<List<WatchItem>>

    @Query("Select * FROM watchItem WHERE id = :id")
    abstract fun getById(id : UUID) : List<WatchItem>
}

@Dao
abstract class MovieDao : BaseDao<Movie>{
    @Query("SELECT * FROM movie")
    abstract fun getAll() : Flow<List<Movie>>

    @Query("Select * FROM movie WHERE id IN(:ids)")
    abstract fun getAllByIds(ids : List<Int>) : List<Movie>
}