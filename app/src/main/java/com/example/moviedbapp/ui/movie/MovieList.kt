package com.example.moviedbapp.ui.movie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviedbapp.R
import com.example.moviedbapp.domain.Movie
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@Composable
fun MovieList(movies: List<Movie>, modifier: Modifier = Modifier) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {

        items(items = movies, key = { it.id }) { item ->
            MovieItem(
                movie = item,
                modifier = modifier.padding(dimensionResource(id = R.dimen.padding_small)))
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
    MovieList(movies = movies)
}


@Composable
fun MovieItem(movie: Movie, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Text(
                text = movie.title ?: "",
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = movie.id.toString(),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}