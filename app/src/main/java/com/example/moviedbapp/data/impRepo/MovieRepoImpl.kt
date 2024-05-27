package com.example.moviedbapp.data.impRepo

import android.util.Log
import com.example.moviedbapp.data.network.MovieDbApiService
import com.example.moviedbapp.data.network.Result
import com.example.moviedbapp.data.room.MovieDao
import com.example.moviedbapp.data.room.ProfileDao
import com.example.moviedbapp.data.room.WatchItem
import com.example.moviedbapp.data.room.WatchItemDao
import com.example.moviedbapp.domain.Movie
import com.example.moviedbapp.domain.MovieRepository
import kotlinx.coroutines.flow.flow

class MovieRepoImpl(
    val movieDao: MovieDao,
    val profileDao: ProfileDao,
    val watchItemDao: WatchItemDao,
    val movieDbApiService: MovieDbApiService
) : MovieRepository {

    override fun getDiscoverMovies() = flow {
        emit(movieDbApiService.getDiscoverMovies().results?.mapNotNull { it?.toMovie() }
            ?: emptyList())
    }

    override suspend fun addMovieToWishList(isFavorite: Boolean, movie: Movie) {
        val selectedProfile = profileDao.getSelected().first()
        Log.d("Selected Profile is ", isFavorite.toString())
        val watchItem = WatchItem(
            profileId = selectedProfile.id,
            movieDbId = movie.id
        )
        if (isFavorite) {
            movieDao.insert(
                com.example.moviedbapp.data.room.Movie(
                    name = movie.title ?: "",
                    movieDbId = movie.id,
                    poster = movie.poster ?: ""
                )
            )
            watchItemDao.insert(watchItem)
        } else {
            watchItemDao.delete(watchItem)
        }
    }
}

private const val imagePrefix = "https://image.tmdb.org/t/p/w500"
fun Result.toMovie() = Movie(
    id = id ?: 0,
    title = title,
    poster = if (poster_path?.isNotEmpty() == true) imagePrefix + poster_path else null,
    overview = overview
)
