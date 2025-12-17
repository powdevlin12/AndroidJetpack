package com.dattran.unitconverter.movie_project.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.dattran.unitconverter.movie_project.data.model.Movie
import com.dattran.unitconverter.movie_project.ui.screens.home.HomeViewModel

@Composable
fun MovieItem(
    movie: Movie,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = { viewModel.onClickItemDelete(movie = movie) }),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {
            // Poster Image
//            AsyncImage(
//                model = movie.getPosterUrl(),
//                contentDescription = movie.title,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .width(100.dp)
//                    .height(150.dp)
//                    .clip(RoundedCornerShape(8.dp))
//            )

            Spacer(modifier = Modifier.width(12.dp))

            // Movie Info
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "⭐ ${String.format("%.1f", movie.voteAverage)}/10",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "📅 ${movie.releaseDate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}