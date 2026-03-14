package com.dattran.unitconverter.social.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dattran.unitconverter.social.data.local.dao.UserDao
import com.dattran.unitconverter.social.data.local.entity.UserEntity
import com.dattran.unitconverter.social.data.repository.UserRepository
import com.dattran.unitconverter.social.data.service.AuthApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ProfileState(
    val isLoading: Boolean = false,
    val errorMsg: String = "",
    val profileDataLocal: UserEntity? = null
)

class ProfileViewModel(
    private val userDao: UserDao
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileState())
    val uiState: StateFlow<ProfileState> = _uiState.asStateFlow()
    private val repository = UserRepository(userDao, AuthApiService.create())

    init {
        // ⭐ Collect Flow — mỗi khi DB thay đổi (sau updateProfileFieldsLocal),
        // profileDataLocal sẽ tự cập nhật, UI re-compose tự động
        viewModelScope.launch {
            repository.getUserLocal().collect { user ->
                _uiState.value = _uiState.value.copy(profileDataLocal = user)
            }
        }
    }

    fun handleLogout(accessToken: String, refreshToken: String) {
        viewModelScope.launch {
            repository.logout(authorization = accessToken, refreshToken = refreshToken)
                .fold(
                    onSuccess = { println("Logout successful: $it") },
                    onFailure = { println("Logout failed: ${it.message}") }
                )
        }
    }
}