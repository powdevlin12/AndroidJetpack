package com.dattran.unitconverter.social.data.repository

import com.dattran.unitconverter.social.data.local.dao.UserDao
import com.dattran.unitconverter.social.data.local.entity.UserEntity
import com.dattran.unitconverter.social.data.model.UserLoginResponse
import com.dattran.unitconverter.social.data.model.UserLogoutBody
import com.dattran.unitconverter.social.data.model.UserLogoutResponse
import com.dattran.unitconverter.social.data.model.UserUpdateBody
import com.dattran.unitconverter.social.data.service.AuthApiService
import com.dattran.unitconverter.social.data.service.MovieApiService
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val userDao: UserDao,
    private val apiService: AuthApiService,
) {
    suspend fun insertUserInfo(user: UserEntity) {
        userDao.insertUser(user)
    }

    // ⭐ Query
    suspend fun getUserById(userId: Int): Flow<UserEntity?> {
        return userDao.getUserById(userId)
    }

    // ⭐ Query
    suspend fun getUserLocal(): UserEntity? {
        return userDao.getUserLocal()
    }

    // ⭐ Patch
    suspend fun updateUser(userUpdate: UserUpdateBody) {
        val user = userDao.getUserLocal()
        apiService.update(
            authorization = "Bearer " + (user?.accessToken ?: ""),
            userId = user?.id ?: "",
            user = userUpdate
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