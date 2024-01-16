package com.zm.model

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