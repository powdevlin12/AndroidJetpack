package com.dattran.unitconverter.movie_project.data.service

import com.dattran.unitconverter.movie_project.data.model.BodyUpdateMovie
import com.dattran.unitconverter.movie_project.data.model.DeleteMovieResponse
import com.dattran.unitconverter.movie_project.data.model.Movie
import com.dattran.unitconverter.movie_project.data.model.MovieByIdResponse
import com.dattran.unitconverter.movie_project.data.model.MovieResponse
import com.dattran.unitconverter.movie_project.data.model.UpdateMovieResponse
import com.dattran.unitconverter.movie_project.data.model.UserLoginBody
import com.dattran.unitconverter.movie_project.data.model.UserLoginResponse
import com.dattran.unitconverter.movie_project.data.model.UserRegisterBody
import com.dattran.unitconverter.movie_project.data.model.UserRegisterResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("movies")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1,
    ): MovieResponse

    @POST("movies")
    suspend fun createMovies(
        @Body movie: BodyUpdateMovie,
    ): UpdateMovieResponse

    @GET("movies/{movieId}")
    suspend fun getMovieById(
        @Path("movieId") movieId: String = "000",
    ): MovieByIdResponse

    @DELETE("movies/{id}")
    suspend fun deleteMovie(
        @Path("id") movieId: String,
    ): DeleteMovieResponse

    @PATCH("movies/{id}")
    suspend fun updateMovie(
        @Path("id") movieId: String,
        @Body movie: BodyUpdateMovie
    ): UpdateMovieResponse

    // authentication
    @POST("users/register")
    suspend fun register(
        @Body user: UserRegisterBody,
    ): UserRegisterResponse

    @POST("users/login")
    suspend fun login(
        @Body user: UserLoginBody,
    ): UserLoginResponse

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