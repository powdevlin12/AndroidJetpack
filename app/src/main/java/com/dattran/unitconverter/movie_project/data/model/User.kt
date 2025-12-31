package com.dattran.unitconverter.movie_project.data.model

data class UserRegisterBody(
    val name: String,
    val email: String,
    val password: String,
    val confirm_password: String,
    val date_of_birth: String? = "2000-01-01"
)

data class Token(
    val accessToken: String,
    val refreshToken: String
)


data class UserRegisterResponse(
    val message: String,
    val data: Token
)

data class UserLoginBody(
    val email: String,
    val password: String,
)

data class UserLoginResponse(
    val message: String,
    val data: Token
)

data class UserLogoutBody(
    val refreshToken: String
)

data class UserLogoutResponse(
    val message: String
)