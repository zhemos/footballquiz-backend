package com.zm.modules.user

import com.zm.api.user.UserApi
import com.zm.config.TokenProvider
import com.zm.model.User
import com.zm.modules.BaseController
import org.koin.core.component.inject

class UserControllerImpl : BaseController(), UserController {

    private val userApi by inject<UserApi>()
    private val tokenProvider by inject<TokenProvider>()

    override suspend fun getUser(token: String): User? {
        tokenProvider.verifyToken(token)?.let {
            return userApi.getUserById(it)
        }
        return null
    }
}