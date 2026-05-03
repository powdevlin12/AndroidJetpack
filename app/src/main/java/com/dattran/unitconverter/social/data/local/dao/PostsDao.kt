package com.dattran.unitconverter.social.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dattran.unitconverter.social.data.local.entity.PostsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(user: List<PostsEntity>)

    // Get user information by ID
    @Query("SELECT * FROM posts LIMIT 10")
    suspend fun getPosts(): List<PostsEntity>
}