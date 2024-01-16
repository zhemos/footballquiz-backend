package com.zm.modules.auth

import com.zm.api.user.UserApi
import com.zm.config.TokenProvider
import com.zm.model.CredentialsResponse
import com.zm.model.LoginCredentials
import com.zm.model.RefreshBody
import com.zm.modules.BaseController
import com.zm.statuspages.AuthenticationException
import org.koin.core.component.inject

class AuthControllerImpl : BaseController(), AuthController {

    private val userApi by inject<UserApi>()
    private val tokenProvider by inject<TokenProvider>()

    override suspend fun login(credentials: LoginCredentials): CredentialsResponse {
        userApi.getUserByLogin(credentials.login)?.let { user ->
            return tokenProvider.createTokens(user)
        } ?: throw AuthenticationException("Wrong credentials")
    }

    override suspend fun refreshToken(refreshBody: RefreshBody): CredentialsResponse {
        tokenProvider.verifyToken(refreshBody.refreshToken)?.let { userId ->
            userApi.getUserById(userId)?.let { user ->
                return tokenProvider.createTokens(user)
            } ?: throw AuthenticationException("Wrong credentials")
        } ?: throw AuthenticationException("Wrong credentials")
    }
}