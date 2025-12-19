package com.dattran.unitconverter.movie_project.data.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
) {

}

data class MovieByIdResponse(
    val data: Movie? = null,
    val message: String
)

data class BodyUpdateMovie(
    val title: String = "",
    val overview: String = "",
)

data class UpdateMovieResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: Movie
)

data class DeleteMovieResponse(
    @SerializedName("message")
    val message: Int,
    @SerializedName("data")
    val data: Movie
)


