package com.zm.footballquiz.model.dto

data class CreateUserBody(
    val login: String,
    val email: String,
    val password: String,
    val country: String,
)

data class LoginCredentialsBody(
    val login: String,
    val password: String,
)

data class RefreshBody(
    val refreshToken: String,
)

data class CredentialsResponse(
    val accessToken: String,
    val refreshToken: String,
    val expiresInMs: Long,
)