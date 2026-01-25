package com.dattran.unitconverter.social.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dattran.unitconverter.social.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    // Insert or update user information
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    // Get user information by ID
    @Query("SELECT * FROM user_info WHERE id = :userId LIMIT 1")
    fun getUserById(userId: Int): Flow<UserEntity?>
}