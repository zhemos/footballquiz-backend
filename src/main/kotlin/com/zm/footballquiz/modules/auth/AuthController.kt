package com.zm.footballquiz.modules.auth

import com.zm.footballquiz.model.CreateUserBody
import com.zm.footballquiz.model.CredentialsResponse
import com.zm.footballquiz.model.LoginCredentials
import com.zm.footballquiz.model.RefreshBody

interface AuthController {

    suspend fun createUser(createUserBody: CreateUserBody): CredentialsResponse

    suspend fun login(credentials: LoginCredentials): CredentialsResponse

    suspend fun refreshToken(refreshBody: RefreshBody): CredentialsResponse
}