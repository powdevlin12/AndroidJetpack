package com.dattran.unitconverter.social.data.repository

import com.dattran.unitconverter.social.data.local.dao.PostsDao
import com.dattran.unitconverter.social.data.local.dao.UserDao
import com.dattran.unitconverter.social.data.local.entity.PostsEntity
import com.dattran.unitconverter.social.data.model.GetPostResponse
import com.dattran.unitconverter.social.data.model.TweetCreateBody
import com.dattran.unitconverter.social.data.model.TweetCreateResponse
import com.dattran.unitconverter.social.data.service.AuthApiService
import kotlinx.coroutines.flow.first

class TweetRepository(
    private val apiService: AuthApiService,
    private val userDao: UserDao,
    private val postDao: PostsDao,
) {
    suspend fun addTweet(
        newTweet: TweetCreateBody,
    ): Result<TweetCreateResponse> {
        return try {
            val user = userDao.getUserLocal().first()

            val response = apiService.createTweet(
                authorization = "Bearer " + (user?.accessToken ?: ""),
                tweet = newTweet
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTweetsLocal(): Result<List<PostsEntity>> {
        return try {
            val res = postDao.getPosts()
            Result.success(res)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTweets(): Result<GetPostResponse> {
        return try {
            val user = userDao.getUserLocal().first()

            val response = apiService.getTweets(
                authorization = "Bearer " + (user?.accessToken ?: ""),
            )

            val listPostSaveLocal: MutableList<PostsEntity> = mutableListOf()

            for (post in response.data) {
                listPostSaveLocal.add(
                    PostsEntity(
                        id = post._id,
                        nameUser = post.user.name,
                        content = post.content,
                        userViews = post.user_views,
                        email = post.user.email,
                        createAt = post.created_at
                    )
                )
            }

            postDao.insertPosts(listPostSaveLocal);
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}