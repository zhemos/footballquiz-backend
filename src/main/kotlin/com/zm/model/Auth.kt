package com.zm.model

data class CreateUserBody(
    val login: String,
    val email: String,
    val password: String,
    val dateOfBirth: String,
    val country: String
)

data class LoginCredentials(
    val login: String,
    val password: String
)

data class RefreshBody(
    val refreshToken: String
)

data class CredentialsResponse(
    val accessToken: String,
    val refreshToken: String,
    val expiresInMs: Long
)