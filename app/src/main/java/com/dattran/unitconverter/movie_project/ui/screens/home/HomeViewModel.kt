package com.dattran.unitconverter.movie_project.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dattran.unitconverter.movie_project.data.model.Movie
import com.dattran.unitconverter.movie_project.data.repository.MovieRepository
import com.dattran.unitconverter.movie_project.data.service.MovieApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// dùng data class để dễ dàng copy state bằng copy(
// bất biến -> tránh bug
// dễ so sánh -> Tối ưu recomposition
data class HomeUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val movies: List<Movie> = emptyList()
)

// ViewModel chịu trách nhiệm: Gọi repository, Xử lý logic, quản lý state, không vẽ UI
class HomeViewModel(
) : ViewModel() {
    private val repository = MovieRepository(MovieApiService.create())

    // _uiState dùng để ViewModel ghi
    private val _uiState = MutableStateFlow(HomeUiState())

    // uiState dùng để UI đọc, UI không sửa state
    // Vì sao luôn dùng StateFlow: Luôn có giá trị hiện tại, An toàn với lifecycle, rất hợp với Compose(collectAsState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    // gọi init ở đây -> ViewModel sinh ra -> load data, UI không cần gọi thủ công, tránh gọi nhiều lần khi recomposition
    init {
        Log.i("HomeViewModel", "Initialized")
        loadMovies()
    }

    fun loadMovies() {
        // viewModelScope.lunch: CoroutineScope được gắn với vòng đời của ViewModel, ViewModel bị destroy -> coroutin tự cancel
        viewModelScope.launch {
            // Khi bắt đầu load -> bật loading. Xoá lỗi cũ
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            repository.getMovies().fold(
                onSuccess = { response ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        movies = response.results
                    )
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Unknown error occurred"
                    )
                }
            )
        }
    }
}