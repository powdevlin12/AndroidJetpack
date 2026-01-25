package com.dattran.unitconverter.social.ui.screens.profile

import androidx.lifecycle.ViewModel
import com.dattran.unitconverter.social.data.local.dao.UserDao
import com.dattran.unitconverter.social.data.local.entity.UserEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
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

    fun handleSetProfileDataLocal(user: UserEntity?) {
        _uiState.value = _uiState.value.copy(
            profileDataLocal = user
        )
    }

    fun handleGetUserDataLocal() {
        viewModelScope.launch {
            val userEntity = userDao.getUserLocal()
            handleSetProfileDataLocal(user = userEntity)
        }
    }
}