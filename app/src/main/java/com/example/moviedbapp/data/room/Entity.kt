package com.example.moviedbapp.data.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.UUID

@Entity(tableName = "profile",)
data class Profile(
    val name: String,
    var selected : Boolean = false,
    @PrimaryKey val id: UUID = UUID.randomUUID(),
)

@Entity(tableName = "movie")
data class Movie(
    val name: String,
    @PrimaryKey val id: UUID = UUID.randomUUID(),
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
    val profileId : UUID,
    val movieId: UUID,
    @PrimaryKey val id: UUID = UUID.randomUUID(),
)

data class ProfileAndWatchList(
    @Embedded val profile: Profile,
    @Relation(
        parentColumn = "id",
        entityColumn = "profileId"
    )
    val watchList: List<WatchItem>
)

