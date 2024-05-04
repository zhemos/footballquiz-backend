package com.zm.footballquiz.modules.auth

import com.zm.footballquiz.api.user.UserApi
import com.zm.footballquiz.config.TokenProvider
import com.zm.footballquiz.model.User
import com.zm.footballquiz.model.dto.CreateUserBody
import com.zm.footballquiz.model.dto.CredentialsResponse
import com.zm.footballquiz.model.dto.LoginCredentialsBody
import com.zm.footballquiz.model.dto.RefreshBody
import com.zm.footballquiz.modules.BaseController
import com.zm.footballquiz.statuspages.ApplicationException
import com.zm.footballquiz.util.PasswordManagerContract
import com.zm.footballquiz.util.isEmailValid
import com.zm.footballquiz.util.isPasswordValid
import org.koin.core.component.inject

class AuthControllerImpl : BaseController(), AuthController {

    private val userApi by inject<UserApi>()
    private val passwordManager by inject<PasswordManagerContract>()
    private val tokenProvider by inject<TokenProvider>()

    override suspend fun createUser(
        createUserBody: CreateUserBody,
        userRole: User.Role
    ): CredentialsResponse = with(createUserBody) {
        if (email.isEmailValid().not()) {
            throw ApplicationException.Generic("invalid email")
        }
        if (password.isPasswordValid().not()) {
            throw ApplicationException.Generic("invalid password")
        }
        val user = dbQuery {
            userApi.getUserByLoginOrEmail(login, email)?.let {
                throw ApplicationException.Generic("user was already created")
            }
            userApi.createUser(this, userRole)
        }
        return tokenProvider.createTokens(user)
    }

    override suspend fun login(credentials: LoginCredentialsBody): CredentialsResponse = dbQuery {
        userApi.getUserByLogin(credentials.login)?.let { user ->
            if (passwordManager.validatePassword(credentials.password, user.password)) {
                return@dbQuery tokenProvider.createTokens(user)
            } else throw ApplicationException.Generic("incorrect password")
        } ?: throw ApplicationException.DataNotFound
    }

    override suspend fun refreshToken(refreshBody: RefreshBody): CredentialsResponse {
        tokenProvider.verifyToken(refreshBody.refreshToken)?.let { userId ->
            val user = dbQuery {
                userApi.getUserById(userId) ?: throw ApplicationException.DataNotFound
            }
            return tokenProvider.createTokens(user)
        } ?: throw ApplicationException.Generic("refresh token")
    }
}