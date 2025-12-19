package com.dattran.unitconverter.movie_project.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dattran.unitconverter.movie_project.data.model.Movie
import com.dattran.unitconverter.movie_project.ui.screens.home.HomeViewModel

@Composable
fun MovieList(movies: List<Movie>, viewModel: HomeViewModel) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(movies) { movie ->
            MovieItem(
                movie = movie,
                viewModel = viewModel
            )
        }
    }
}