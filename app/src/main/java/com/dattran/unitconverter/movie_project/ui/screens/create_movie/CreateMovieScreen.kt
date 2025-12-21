package com.dattran.unitconverter.movie_project.ui.screens.create_movie

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dattran.unitconverter.movie_project.data.model.BodyUpdateMovie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateMovieScreen(viewModel: CreateMovieViewModel = CreateMovieViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🎬 Update Movies") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },

        ) { padding ->
        Box(modifier = Modifier.padding(paddingValues = padding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { it ->
                        title = it
                    },
                    label = { Text("Tiêu đề") },
                    modifier = Modifier.fillMaxWidth(),
//                    isError = uiState.titleError != null,
//                    supportingText = {
//                        if (uiState.titleError != null) {
//                            Text(
//                                text = uiState.titleError!!,
//                                color = MaterialTheme.colorScheme.error
//                            )
//                        }
//                    },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { it ->
                        description = it
                    },
                    label = { Text("Nội dung") },
                    modifier = Modifier.fillMaxWidth(),
//                    isError = uiState.descriptionError != null,
//                    supportingText = {
//                        if (uiState.descriptionError != null) {
//                            Text(
//                                text = uiState.descriptionError!!,
//                                color = MaterialTheme.colorScheme.error
//                            )
//                        }
//                    },
                    maxLines = 5
                )
                Spacer(modifier = Modifier.height(12.dp))
                when {
                    !uiState.isLoading -> {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth(), onClick = {
                                viewModel.handleCreateMovie(
                                    BodyUpdateMovie(
                                        title = title,
                                        overview = description
                                    )
                                )
                            }) {
                            Text("Thêm phim")
                        }
                    }

                    else -> {
                        Box {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }

            }
        }
    }
}

@Preview()
@Composable()
fun CreateMoviePreview() {
    CreateMovieScreen()
}