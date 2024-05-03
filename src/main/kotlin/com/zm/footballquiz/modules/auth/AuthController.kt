package com.zm.footballquiz.modules.auth

import com.zm.footballquiz.model.dto.CreateUserBody
import com.zm.footballquiz.model.dto.CredentialsResponse
import com.zm.footballquiz.model.dto.LoginCredentials
import com.zm.footballquiz.model.dto.RefreshBody

interface AuthController {

    suspend fun createUser(createUserBody: CreateUserBody): CredentialsResponse

    suspend fun login(credentials: LoginCredentials): CredentialsResponse

    suspend fun refreshToken(refreshBody: RefreshBody): CredentialsResponse
}