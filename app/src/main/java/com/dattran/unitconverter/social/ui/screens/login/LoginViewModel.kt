package com.dattran.unitconverter.social.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.dattran.unitconverter.social.data.local.UserPreferences
import com.dattran.unitconverter.social.data.local.dao.UserDao
import com.dattran.unitconverter.social.data.local.entity.UserEntity
import com.dattran.unitconverter.social.data.model.UserLoginBody
import com.dattran.unitconverter.social.data.repository.MovieRepository
import com.dattran.unitconverter.social.data.repository.UserRepository
import com.dattran.unitconverter.social.data.service.MovieApiService
import com.dattran.unitconverter.social.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginState(
    val isLoading: Boolean = false,
    val errorMsg: String = ""
)

class LoginViewModel(
    val userPreferences: UserPreferences,
    private val userDao: UserDao
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()
    private val repository = MovieRepository(MovieApiService.create())
    private val userInfoRepository = UserRepository(userDao = userDao)

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

    fun handleLogin(userForm: UserLoginBody, navController: NavController) {
        if (!handleValidateForm(userForm)) {
            handleSetErrorMsg("Email hoặc mật khẩu chưa đúng định dạng")
            return
        }
        onChangeStateLoading(true)
        viewModelScope.launch {
            repository.login(
                userForm = userForm
            ).fold(
                onSuccess = { response ->
                    val token = response.data.accessToken

                    // ⭐ Lưu token vào DataStore
                    userPreferences.saveLoginInfo(
                        token = token,
                    )

                    // ⭐ Gọi API get-me để lấy thông tin user đầy đủ
                    repository.getMe(token).fold(
                        onSuccess = { userResponse ->
                            val userInfo = userResponse.data

                            // ⭐ Lưu thông tin user vào Room Database
                            val userEntity = UserEntity(
                                id = userInfo._id,
                                name = userInfo.name,
                                email = userInfo.email,
                                avatar = userInfo.avatar
                            )

                            viewModelScope.launch {
                                userInfoRepository.insertUserInfo(userEntity)
                            }

                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                errorMsg = ""
                            )

                            navController.navigate(Screen.Home.route)
                        },
                        onFailure = { error ->
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                errorMsg = "Không thể lấy thông tin người dùng: ${error.message}"
                            )
                        }
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