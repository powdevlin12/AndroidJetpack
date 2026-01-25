package com.dattran.unitconverter.social.data.repository

import com.dattran.unitconverter.social.data.local.dao.UserDao
import com.dattran.unitconverter.social.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val userDao: UserDao
) {
    suspend fun insertUserInfo(user: UserEntity) {
        userDao.insertUser(user)
    }

    // ⭐ Query
    suspend fun getUserById(userId: Int): Flow<UserEntity?> {
        return userDao.getUserById(userId)
    }
}