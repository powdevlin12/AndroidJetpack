package com.dattran.unitconverter.social.data.service

import com.dattran.unitconverter.social.data.model.GetPostResponse
import com.dattran.unitconverter.social.data.model.TweetCreateBody
import com.dattran.unitconverter.social.data.model.TweetCreateResponse
import com.dattran.unitconverter.social.data.model.UserLogoutBody
import com.dattran.unitconverter.social.data.model.UserLogoutResponse
import com.dattran.unitconverter.social.data.model.UserUpdateBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthApiService {
    // authentication
    @POST("users/logout")
    suspend fun logout(
        @Header("Authorization") authorization: String,
        @Body user: UserLogoutBody,
    ): UserLogoutResponse

    @PATCH("users/{user_id}")
    suspend fun update(
        @Header("Authorization") authorization: String,
        @Path("user_id") userId: String,
        @Body user: UserUpdateBody,
    ): UserLogoutResponse

    //    Post tweet
    @POST("tweets/")
    suspend fun createTweet(
        @Header("Authorization") authorization: String,
        @Body tweet: TweetCreateBody,
    ): TweetCreateResponse


    @GET("tweets/")
    suspend fun getTweets(
        @Header("Authorization") authorization: String
    ): GetPostResponse

    companion object {
        private const val BASE_URL = "http://10.0.2.2:1236/"

        fun create(): AuthApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AuthApiService::class.java)
        }
    }
}