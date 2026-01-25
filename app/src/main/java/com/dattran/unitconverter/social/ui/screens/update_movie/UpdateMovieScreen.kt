package com.dattran.unitconverter.social.ui.screens.update_movie

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dattran.unitconverter.social.ui.components.AlertCustom

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateMovieScreen(
    movieId: String,
    viewModel: UpdateViewModel = UpdateViewModel(),
    navController: NavController
) {
    LaunchedEffect(movieId) {
        viewModel.getMovieById(movieId)
    }

    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🎬 Update Movies") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),

                )
        },

        ) { padding ->
        Box(modifier = Modifier.padding(paddingValues = padding)) {

            if (uiState.msgSuccess != null) {
                AlertCustom(
                    title = "Thông báo",
                    message = uiState.msgSuccess ?: "",
                    onlyOk = true,
                    handleConfirm = {
                        viewModel.updateMsgSuccess(null);
                        navController.popBackStack();
                    }
                )
            }

            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                uiState.errorMsg.isNotEmpty() -> {
                    Text(
                        text = uiState.errorMsg,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    uiState.movie?.let { movie ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            OutlinedTextField(
                                value = uiState.title,
                                onValueChange = { it ->
                                    viewModel.onTitleChange(it)
                                },
                                label = { Text("Tiêu đề") },
                                modifier = Modifier.fillMaxWidth(),
                                isError = uiState.titleError != null,
                                supportingText = {
                                    if (uiState.titleError != null) {
                                        Text(
                                            text = uiState.titleError!!,
                                            color = MaterialTheme.colorScheme.error
                                        )
                                    }
                                },
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedTextField(
                                value = uiState.description,
                                onValueChange = { },
                                label = { Text("Nội dung") },
                                modifier = Modifier.fillMaxWidth(),
                                isError = uiState.descriptionError != null,
                                supportingText = {
                                    if (uiState.descriptionError != null) {
                                        Text(
                                            text = uiState.descriptionError!!,
                                            color = MaterialTheme.colorScheme.error
                                        )
                                    }
                                },
                                maxLines = 5
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth(), onClick = { viewModel.handleUpdateMovie() }) {
                                Text("Cập nhật")
                            }
                        }
                    }
                }
            }
        }
    }
}