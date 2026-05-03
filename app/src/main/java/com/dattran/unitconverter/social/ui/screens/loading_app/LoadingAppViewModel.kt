package com.dattran.unitconverter.social.ui.screens.loading_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.dattran.unitconverter.social.data.local.dao.UserDao
import com.dattran.unitconverter.social.data.local.entity.UserEntity
import com.dattran.unitconverter.social.data.repository.MovieRepository
import com.dattran.unitconverter.social.data.repository.UserRepository
import com.dattran.unitconverter.social.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class LoadingAppState(
    val isLoading: Boolean,
    val errorMsg: String
)

class LoadingAppViewModel(
    private val repository: UserRepository,
    private val userDao: UserDao,
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoadingAppState(isLoading = true, errorMsg = ""))
    val appData = _uiState.asStateFlow()

    fun handleGetUserInfoWhenStartApp(
        onSuccessful: () -> Unit,
        onFail: () -> Unit
    ) {
        viewModelScope.launch {
            val user = userDao.getUserLocal().first()
            val token = user?.accessToken ?: ""

            repository.getMe(token).fold(
                onSuccess = { userResponse ->
                    val userInfo = userResponse.data

                    // ⭐ Lưu thông tin user vào Room Database
                    val userEntity = UserEntity(
                        id = userInfo._id,
                        name = userInfo.name,
                        email = userInfo.email,
                        avatar = userInfo.avatar,
                        bio = userInfo.bio,
                        website = userInfo.website,
                        location = userInfo.location,
                        dateOfBirth = userInfo.date_of_birth,
                        refreshToken = user?.refreshToken ?: "",
                        accessToken = user?.accessToken ?: "",
                        verify = userInfo.verify,
                        createdAt = userInfo.created_at,
                        updatedAt = userInfo.updated_at
                    )


                    repository.insertUserInfo(userEntity)
                    onSuccessful();
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMsg = ""
                    )

                },
                onFailure = { error ->
                    onFail()
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMsg = "Không thể lấy thông tin người dùng: ${error.message}"
                    )
                }
            )
        }
    }
}