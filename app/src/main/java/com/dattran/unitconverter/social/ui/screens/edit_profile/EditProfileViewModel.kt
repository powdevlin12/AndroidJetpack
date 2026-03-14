package com.dattran.unitconverter.social.ui.screens.edit_profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dattran.unitconverter.social.data.local.dao.UserDao
import com.dattran.unitconverter.social.data.model.UserUpdateBody
import com.dattran.unitconverter.social.data.repository.UserRepository
import com.dattran.unitconverter.social.data.service.AuthApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class EditProfileState(
    val name: String = "",
    val bio: String = "",
    val website: String = "",
    val location: String = "",
    val email: String = "",
    val isLoading: Boolean = false,
    val errorMsg: String = ""
)

class EditProfileViewModel(
    private val userDao: UserDao
) : ViewModel() {
    private val _uiState = MutableStateFlow(EditProfileState())
    val uiState: StateFlow<EditProfileState> = _uiState.asStateFlow()

    private val userInfoRepository = UserRepository(userDao = userDao, AuthApiService.create())

    init {
        loadUserData()
    }

    fun loadUserData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val user =
                userInfoRepository.getUserLocal().first()  // ⭐ .first() để lấy snapshot từ Flow
            if (user != null) {
                _uiState.update { state ->
                    state.copy(
                        name = user.name,
                        bio = user.bio ?: "",
                        website = user.website ?: "",
                        location = user.location ?: "",
                        email = user.email,
                        isLoading = false
                    )
                }
            } else {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onFieldChange(field: FormField, value: String) {
        _uiState.update { state ->
            when (field) {
                FormField.NAME -> state.copy(name = value)
                FormField.BIO -> state.copy(bio = value)
                FormField.WEBSITE -> state.copy(website = value)
                FormField.LOCATION -> state.copy(location = value)
                FormField.EMAIL -> state.copy(email = value)
            }
        }
    }

    fun updateUser() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                Log.d("EditProfileViewModel", "updateUser: calling API...")
                // ⭐ Bước 1: Gọi API update — nếu thất bại sẽ throw exception, không chạy tiếp
                userInfoRepository.updateUser(
                    UserUpdateBody(
                        name = uiState.value.name,
                        bio = uiState.value.bio,
                        website = uiState.value.website,
                        location = uiState.value.location,
                        email = uiState.value.email,
                        avatar = ""
                    )
                )
                // ⭐ Bước 2: API thành công → mới update local, chỉ update các field profile
                // Tương tự spread operator {...user, name, bio, ...} trong JS
                userInfoRepository.updateProfileFieldsLocal(
                    name = uiState.value.name,
                    bio = uiState.value.bio,
                    website = uiState.value.website,
                    location = uiState.value.location,
                    email = uiState.value.email,
                )
                Log.d("EditProfileViewModel", "updateUser: success!")
                _uiState.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                Log.e("EditProfileViewModel", "updateUser error: ${e.message}", e)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMsg = e.message ?: "An error occurred"
                    )
                }
            }
        }
    }
}