package com.dattran.unitconverter.social.data.repository

import com.dattran.unitconverter.social.data.local.dao.UserDao
import com.dattran.unitconverter.social.data.local.entity.UserEntity
import com.dattran.unitconverter.social.data.model.UserLogoutBody
import com.dattran.unitconverter.social.data.model.UserLogoutResponse
import com.dattran.unitconverter.social.data.model.UserUpdateBody
import com.dattran.unitconverter.social.data.service.AuthApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class UserRepository(
    private val userDao: UserDao,
    private val apiService: AuthApiService,
) {
    suspend fun insertUserInfo(user: UserEntity) {
        userDao.insertUser(user)
    }

    // ⭐ Query — trả về Flow, UI tự cập nhật khi DB thay đổi
    fun getUserLocal(): Flow<UserEntity?> {
        return userDao.getUserLocal()
    }

    // ⭐ Patch
    suspend fun updateUser(userUpdate: UserUpdateBody) {
        val user = userDao.getUserLocal().first()  // lấy snapshot hiện tại
        apiService.update(
            authorization = "Bearer " + (user?.accessToken ?: ""),
            userId = user?.id ?: "",
            user = userUpdate
        )
    }


    // ⭐ Chỉ update các field profile, giữ nguyên accessToken, refreshToken, avatar, ...
    suspend fun updateProfileFieldsLocal(
        name: String,
        bio: String,
        website: String,
        location: String,
        email: String,
    ) {
        userDao.updateProfileFields(
            name = name,
            bio = bio,
            website = website,
            location = location,
            email = email,
        )
    }

    suspend fun logout(
        authorization: String,
        refreshToken: String,
    ): Result<UserLogoutResponse> {
        return try {
            val response = apiService.logout(
                authorization = authorization,
                user = UserLogoutBody(refreshToken = refreshToken)
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}