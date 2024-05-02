package com.zm.modules.auth

import com.zm.api.user.UserApi
import com.zm.config.TokenProvider
import com.zm.model.CreateUserBody
import com.zm.model.CredentialsResponse
import com.zm.model.LoginCredentials
import com.zm.model.RefreshBody
import com.zm.modules.BaseController
import com.zm.statuspages.ApplicationException
import com.zm.util.PasswordManagerContract
import com.zm.util.isEmailValid
import com.zm.util.isPasswordValid
import org.koin.core.component.inject

class AuthControllerImpl : BaseController(), AuthController {

    private val userApi by inject<UserApi>()
    private val passwordManager by inject<PasswordManagerContract>()
    private val tokenProvider by inject<TokenProvider>()

    override suspend fun createUser(createUserBody: CreateUserBody): CredentialsResponse = with(createUserBody) {
        if (email.isEmailValid().not()) {
            throw ApplicationException.InvalidEmail
        }
        if (password.isPasswordValid().not()) {
            throw ApplicationException.InvalidPassword
        }
        val user = dbQuery {
            userApi.getUserByLoginOrEmail(login, email)?.let {
                throw ApplicationException.UserWasAlreadyCreated
            }
            userApi.createUser(this)
        }
        return tokenProvider.createTokens(user)
    }

    override suspend fun login(credentials: LoginCredentials): CredentialsResponse = dbQuery {
        userApi.getUserByLogin(credentials.login)?.let { user ->
            println(credentials.password)
            println(user.password)
            try {
                val a = passwordManager.validatePassword(credentials.password, user.password)
            } catch (e: Exception) {
                println(e.message)
            }
            println("!!!")
//            if (passwordManager.validatePassword(credentials.password, user.password)) {
//                return@dbQuery tokenProvider.createTokens(user)
//            } else throw ApplicationException.IncorrectPassword
            return@dbQuery tokenProvider.createTokens(user)
        } ?: throw ApplicationException.DataNotFound
    }

    override suspend fun refreshToken(refreshBody: RefreshBody): CredentialsResponse {
        tokenProvider.verifyToken(refreshBody.refreshToken)?.let { userId ->
            println("userID: $userId")
            userApi.getUserById(userId)?.let { user ->
                return tokenProvider.createTokens(user)
            } ?: throw ApplicationException.DataNotFound
        } ?: throw ApplicationException.RefreshToken
    }
}