package com.example.moviedbapp.data.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "profile")
data class Profile(
    @PrimaryKey val id: Int,
    val name: String
)

@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey val id: Int,
    val name: String
)
@Entity(
    tableName = "watchItem",
    foreignKeys = [ForeignKey(
        entity = Profile::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("profileId")
    ), ForeignKey(
        entity = Movie::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("movieId")
    )]
)
data class WatchItem(
    @PrimaryKey val id: Int,
    val profileId: Int,
    val movieId: Int
)

data class ProfileAndWatchList(
    @Embedded val profile: Profile,
    @Relation(
        parentColumn = "id",
        entityColumn = "profileId"
    )
    val watchList: List<WatchItem>
)

