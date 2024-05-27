package com.example.moviedbapp.ui.movie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.moviedbapp.R
import com.example.moviedbapp.domain.Movie
import com.example.moviedbapp.ui.home.MovieListMode
import com.example.moviedbapp.ui.home.MovieUiActions
import com.example.moviedbapp.ui.home.MovieUiState


@Composable
fun MovieList(moviesUiState: MovieUiState, modifier: Modifier = Modifier) {
    val movieMode by moviesUiState.mode
    val movies = if (movieMode == MovieListMode.Discover) moviesUiState.discoverMovies else
        moviesUiState.wishlistMovies
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {

        items(items = movies, key = { it.id }) { item ->
            MovieItem(
                movie = item,
                movieUiActions = moviesUiState.actions,
                modifier = modifier.padding(dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Preview
@Composable
fun PreviewMovieList() {
    val movies = listOf(
        Movie(1, title = "Aaaa"),
        Movie(2, title = "Bbbbb"),
        Movie(3, title = "Cccc")
    )
    MovieList(moviesUiState = MovieUiState(discoverMovies = movies))
}


@Composable
fun MovieItem(movie: Movie, modifier: Modifier = Modifier, movieUiActions: MovieUiActions? = null) {
    var isFavorite by remember { mutableStateOf(false) }
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Row {
                Text(
                    modifier = Modifier.padding(start = 6.dp, top = 12.dp),
                    text = movie.title ?: "",
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(Modifier.weight(1f))
                IconToggleButton(
                    checked = isFavorite,
                    onCheckedChange = {
                        isFavorite = !isFavorite
                        movieUiActions?.addToWishList(isFavorite, movie)
                    }
                ) {
                    Icon(
                        imageVector = if (isFavorite) {
                            Icons.Filled.Favorite
                        } else {
                            Icons.Default.FavoriteBorder
                        },
                        contentDescription = null
                    )
                }
            }
            Spacer(Modifier.weight(1f))
            // Example code, no need to copy over
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(movie.poster)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = movie.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .fillMaxWidth()
                )
            }
            Text(
                text = movie.overview ?: "",
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}
