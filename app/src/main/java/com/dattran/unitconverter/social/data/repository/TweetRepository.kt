package com.dattran.unitconverter.social.data.repository

import com.dattran.unitconverter.social.data.local.dao.UserDao
import com.dattran.unitconverter.social.data.model.TweetCreateBody
import com.dattran.unitconverter.social.data.model.TweetCreateResponse
import com.dattran.unitconverter.social.data.service.AuthApiService
import kotlinx.coroutines.flow.first

class TweetRepository(
    private val apiService: AuthApiService,
    private val userDao: UserDao,
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
}