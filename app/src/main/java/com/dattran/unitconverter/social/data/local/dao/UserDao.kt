package com.dattran.unitconverter.social.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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

    @Query("SELECT * FROM user_info LIMIT 1")
    fun getUserLocal(): Flow<UserEntity?>

    @Update
    suspend fun updateDataUserLocal(userData: UserEntity)

    // ⭐ Chỉ update các field profile, giữ nguyên accessToken, refreshToken, avatar, ...
    @Query(
        """
        UPDATE user_info 
        SET name = :name, bio = :bio, website = :website, location = :location, email = :email
        WHERE id = (SELECT id FROM user_info LIMIT 1)
    """
    )
    suspend fun updateProfileFields(
        name: String,
        bio: String,
        website: String,
        location: String,
        email: String,
    )

}