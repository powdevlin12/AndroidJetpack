package com.dattran.unitconverter.social.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dattran.unitconverter.social.data.model.UserRegisterBody
import com.dattran.unitconverter.social.data.repository.MovieRepository
import com.dattran.unitconverter.social.data.service.MovieApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RegisterState(
    val isLoading: Boolean = false,
    val errorMsg: String = "",
)

class RegisterViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterState())
    val uiState: StateFlow<RegisterState> = _uiState.asStateFlow()
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

    fun handleValidateForm(user: UserRegisterBody): Boolean {
        val currentState = _uiState.value

        if (user.name.isBlank() || user.name.length < 5 || user.name.length > 100) {
            _uiState.value = currentState.copy(errorMsg = "Tên không hợp lệ")
            return false
        }

        if (user.email.isBlank()) {
            _uiState.value = currentState.copy(errorMsg = "Email không được bỏ trống")
            return false
        }

        if (user.password.isBlank() || !user.password.equals(user.confirm_password)) {
            _uiState.value = currentState.copy(errorMsg = "Xác nhận mật khẩu không khớp")
            return false
        }

        return true
    }

    fun handleRegister(userForm: UserRegisterBody) {
        if (!handleValidateForm(userForm)) {
            return;
        }
        onChangeStateLoading(true)
        viewModelScope.launch {
            repository.register(
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