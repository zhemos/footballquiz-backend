package com.zm.modules.auth

import com.zm.model.*

interface AuthController {

    suspend fun createUser(createUserBody: CreateUserBody): CredentialsResponse

    suspend fun login(credentials: LoginCredentials): CredentialsResponse

    suspend fun refreshToken(refreshBody: RefreshBody): CredentialsResponse
}