package com.zm.footballquiz.modules.auth

import com.zm.footballquiz.model.User
import com.zm.footballquiz.model.dto.CreateUserBody
import com.zm.footballquiz.model.dto.CredentialsResponse
import com.zm.footballquiz.model.dto.LoginCredentialsBody
import com.zm.footballquiz.model.dto.RefreshBody

interface AuthController {

    suspend fun createUser(createUserBody: CreateUserBody, userRole: User.Role): CredentialsResponse

    suspend fun login(credentials: LoginCredentialsBody): CredentialsResponse

    suspend fun refreshToken(refreshBody: RefreshBody): CredentialsResponse
}