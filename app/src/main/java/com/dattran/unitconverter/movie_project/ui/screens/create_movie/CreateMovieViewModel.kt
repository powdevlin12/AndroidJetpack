package com.dattran.unitconverter.movie_project.ui.screens.create_movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dattran.unitconverter.movie_project.data.model.BodyUpdateMovie
import com.dattran.unitconverter.movie_project.data.repository.MovieRepository
import com.dattran.unitconverter.movie_project.data.service.MovieApiService
import com.dattran.unitconverter.movie_project.ui.screens.home.HomeUiState
import com.dattran.unitconverter.movie_project.ui.screens.update_movie.UpdateViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UIState(
    val isLoading: Boolean = false,
    val errorMsg: String? = "",
) {}

class CreateMovieViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()
    private val repository = MovieRepository(MovieApiService.create())

    fun handleCreateMovie(movieForm: BodyUpdateMovie) {
        _uiState.value = _uiState.value.copy(
            isLoading = true
        )
        viewModelScope.launch {
            repository.createMovie(movieForm).fold(
                onSuccess = { response ->
                    _uiState.value = _uiState.value.copy(
                        errorMsg = "",
                        isLoading = false
                    )
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        errorMsg = error.message ?: "Error",
                        isLoading = false
                    )
                }
            )
        }
    }
}