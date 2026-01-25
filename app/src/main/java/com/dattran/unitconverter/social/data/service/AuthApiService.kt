package com.dattran.unitconverter.social.data.service

import com.dattran.unitconverter.social.data.model.UserLogoutBody
import com.dattran.unitconverter.social.data.model.UserLogoutResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    // authentication
    @POST("users/logout")
    suspend fun logout(
        @Body user: UserLogoutBody,
    ): UserLogoutResponse


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