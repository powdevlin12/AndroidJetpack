package com.dattran.unitconverter.movie_project.data.repository

import com.dattran.unitconverter.movie_project.data.model.DeleteMovieResponse
import com.dattran.unitconverter.movie_project.data.model.MovieResponse
import com.dattran.unitconverter.movie_project.data.service.MovieApiService

class MovieRepository(
    private val apiService: MovieApiService
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

    suspend fun deleteMovie(movieId: String): Result<DeleteMovieResponse> {
        return try {
            val response = apiService.deleteMovie(movieId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}