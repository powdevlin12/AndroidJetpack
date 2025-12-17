package com.dattran.unitconverter.movie_project.data.service

import com.dattran.unitconverter.movie_project.data.model.DeleteMovieResponse
import com.dattran.unitconverter.movie_project.data.model.MovieResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("movies")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1,
    ): MovieResponse

    @DELETE("movies/{id}")
    suspend fun deleteMovie(
        @Path("id") movieId: String,
    ): DeleteMovieResponse

    companion object {
        private const val BASE_URL = "http://10.0.2.2:1236/"

        fun create(): MovieApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieApiService::class.java)
        }
    }
}