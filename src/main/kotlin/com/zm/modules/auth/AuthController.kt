package com.zm.modules.auth

import com.zm.model.CreateUserBody
import com.zm.model.CredentialsResponse
import com.zm.model.LoginCredentials
import com.zm.model.RefreshBody

interface AuthController {

    suspend fun createUser(createUserBody: CreateUserBody): CredentialsResponse

    suspend fun login(credentials: LoginCredentials): CredentialsResponse

    suspend fun refreshToken(refreshBody: RefreshBody): CredentialsResponse
}