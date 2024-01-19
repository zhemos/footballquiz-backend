package com.zm.modules.auth

import com.zm.api.user.UserApi
import com.zm.config.TokenProvider
import com.zm.model.CreateUserBody
import com.zm.model.CredentialsResponse
import com.zm.model.LoginCredentials
import com.zm.model.RefreshBody
import com.zm.modules.BaseController
import com.zm.statuspages.ApplicationException
import org.koin.core.component.inject

class AuthControllerImpl : BaseController(), AuthController {

    private val userApi by inject<UserApi>()
    private val tokenProvider by inject<TokenProvider>()

    override suspend fun createUser(createUserBody: CreateUserBody): CredentialsResponse {
        val user = dbQuery {
            userApi.getUserByLogin(createUserBody.login)?.let {
                throw ApplicationException.UserWasAlreadyCreated
            }
            userApi.createUser(createUserBody) ?: throw UnknownError("Internal server error")
        }
        return tokenProvider.createTokens(user)
    }

    override suspend fun login(credentials: LoginCredentials): CredentialsResponse = dbQuery {
        userApi.getUserByLogin(credentials.login)?.let { user ->
            //todo check password
            return@dbQuery tokenProvider.createTokens(user)
        } ?: throw ApplicationException.DataNotFound
    }

    override suspend fun refreshToken(refreshBody: RefreshBody): CredentialsResponse {
        tokenProvider.verifyToken(refreshBody.refreshToken)?.let { userId ->
            userApi.getUserById(userId)?.let { user ->
                return tokenProvider.createTokens(user)
            } ?: throw ApplicationException.DataNotFound
        } ?: throw ApplicationException.RefreshToken
    }
}