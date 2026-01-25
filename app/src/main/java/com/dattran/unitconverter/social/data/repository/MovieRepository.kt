package com.dattran.unitconverter.social.data.repository

import com.dattran.unitconverter.social.data.model.BodyUpdateMovie
import com.dattran.unitconverter.social.data.model.DeleteMovieResponse
import com.dattran.unitconverter.social.data.model.GetMeResponse
import com.dattran.unitconverter.social.data.model.MovieByIdResponse
import com.dattran.unitconverter.social.data.model.MovieResponse
import com.dattran.unitconverter.social.data.model.UpdateMovieResponse
import com.dattran.unitconverter.social.data.model.UserLoginBody
import com.dattran.unitconverter.social.data.model.UserLoginResponse
import com.dattran.unitconverter.social.data.model.UserRegisterBody
import com.dattran.unitconverter.social.data.model.UserRegisterResponse
import com.dattran.unitconverter.social.data.service.MovieApiService

class MovieRepository(
    private val apiService: MovieApiService,
) {
    // suspend: Hàm có thể tạm dừng mà không block thread. Chỉ được gọi từ coroutine hoặc hàm suspend khác
    suspend fun getMovies(page: Int = 1): Result<MovieResponse> {
        // Sử dụng try-catch để xử lý ngoại lệ khi gọi API
        return try {
            val response = apiService.getPopularMovies(page = page)
            // Result<T> là một sealed class của Koltin để bọc kết quả thành công hoặc thất bại
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createMovie(
        movieForm: BodyUpdateMovie,
    ): Result<UpdateMovieResponse> {
        return try {
            val response = apiService.createMovies(movie = movieForm)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMovieById(movieId: String): Result<MovieByIdResponse> {
        // Sử dụng try-catch để xử lý ngoại lệ khi gọi API
        return try {
            val response = apiService.getMovieById(movieId)
            // Result<T> là một sealed class của Koltin để bọc kết quả thành công hoặc thất bại
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteMovie(movieId: String): Result<DeleteMovieResponse> {
        return try {
            val response = apiService.deleteMovie(movieId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateMovie(
        movieForm: BodyUpdateMovie,
        movieId: String
    ): Result<UpdateMovieResponse> {
        return try {
            val response = apiService.updateMovie(movie = movieForm, movieId = movieId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // authentication
    suspend fun register(
        userForm: UserRegisterBody,
    ): Result<UserRegisterResponse> {
        return try {
            val response = apiService.register(user = userForm)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(
        userForm: UserLoginBody,
    ): Result<UserLoginResponse> {
        return try {
            val response = apiService.login(user = userForm)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMe(token: String): Result<GetMeResponse> {
        return try {
            val response = apiService.getMe(authorization = "Bearer $token")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}