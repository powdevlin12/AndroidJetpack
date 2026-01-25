package com.dattran.unitconverter.social.ui.screens.update_movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dattran.unitconverter.social.data.model.BodyUpdateMovie
import com.dattran.unitconverter.social.data.model.Movie
import com.dattran.unitconverter.social.data.repository.MovieRepository
import com.dattran.unitconverter.social.data.service.MovieApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UpdateState(
    val isLoading: Boolean = false,
    val errorMsg: String = "",
    val movie: Movie? = null,

    // data update
    val title: String = "",
    val description: String = "",

    // error validate
    val titleError: String? = null,
    val descriptionError: String? = null,

    //
    val msgSuccess: String? = null
)

class UpdateViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(UpdateState())
    val uiState: StateFlow<UpdateState> = _uiState.asStateFlow()
    private val repository = MovieRepository(MovieApiService.create())

    fun getMovieById(movieId: String) {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMsg = "")

        viewModelScope.launch {
            repository.getMovieById(movieId).fold(
                onSuccess = { response ->
                    // Xử lý dữ liệu trả về khi thành công
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMsg = "",
                        movie = response.data,
                        title = response.data?.title ?: "",
                        description = response.data?.overview ?: ""
                    )
                },
                onFailure = { error ->
                    // Xử lý lỗi khi gọi API thất bại
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMsg = error.message ?: "Unknown error"
                    )
                }
            )
        }
    }

    fun onTitleChange(newTitle: String) {
        _uiState.value = _uiState.value.copy(title = newTitle, titleError = null)
    }

    fun onDescriptionChange(newDescription: String) {
        _uiState.value = _uiState.value.copy(description = newDescription, descriptionError = null)
    }

    fun updateMsgSuccess(msg: String?) {
        _uiState.value = _uiState.value.copy(msgSuccess = msg)
    }

    fun handleValidateForm(): Boolean {
        var isValid = true
        val currentState = _uiState.value

        if (currentState.title.isBlank()) {
            _uiState.value = currentState.copy(titleError = "Tiêu đề không được bỏ trống")
            isValid = false
        }

        if (currentState.description.isBlank()) {
            _uiState.value = currentState.copy(descriptionError = "Nội dung không được bỏ trống")
            isValid = false
        }

        return isValid
    }

    fun handleUpdateMovie() {
        if (!handleValidateForm()) return;

        viewModelScope.launch {
            repository.updateMovie(
                movieForm = BodyUpdateMovie(
                    title = _uiState.value.title,
                    overview = _uiState.value.description
                ), movieId = uiState.value.movie?.id ?: ""
            ).fold(
                onSuccess = { repository ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        titleError = null,
                        descriptionError = null,
                        msgSuccess = repository.message
                    )

                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMsg = error.message ?: "Unknown error occurred"
                    )
                }
            )
        }
    }
}