package com.dattran.unitconverter.movie_project.ui.screens.login

import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dattran.unitconverter.movie_project.data.model.UserLoginBody
import com.dattran.unitconverter.movie_project.data.model.UserRegisterBody
import com.dattran.unitconverter.movie_project.data.repository.MovieRepository
import com.dattran.unitconverter.movie_project.data.service.MovieApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginState(
    val isLoading: Boolean = false,
    val errorMsg: String = ""
)

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()
    private val repository = MovieRepository(MovieApiService.create())

    fun onChangeStateLoading(loading: Boolean) {
        _uiState.value = _uiState.value.copy(
            isLoading = loading
        )
    }

    fun handleSetErrorMsg(msg: String) {
        _uiState.value = _uiState.value.copy(
            errorMsg = msg
        )
    }

    fun handleValidateForm(user: UserLoginBody): Boolean {
        val currentState = _uiState.value

        if (user.email.isBlank()) {
            _uiState.value = currentState.copy(errorMsg = "Email không được bỏ trống!")
            return false
        }

        if (user.password.isBlank()) {
            _uiState.value = currentState.copy(errorMsg = "Mật khẩu không được bỏ trống!")
            return false
        }

        return true
    }

    fun handleLogin(userForm: UserLoginBody) {
        if (!handleValidateForm(userForm)) {
            handleSetErrorMsg("Email hoặc mật khẩu chưa đúng định dạng")
            return;
        }
        onChangeStateLoading(true)
        viewModelScope.launch {
            repository.login(
                userForm = userForm
            ).fold(
                onSuccess = { repository ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMsg = ""
                    )
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMsg = error.message ?: ""
                    )
                },
            )
        }
    }

}