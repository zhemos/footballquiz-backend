package com.zm.footballquiz.modules.user

import com.zm.footballquiz.api.user.UserApi
import com.zm.footballquiz.model.User
import com.zm.footballquiz.modules.BaseController
import org.koin.core.component.inject

class UserControllerImpl : BaseController(), UserController {

    private val userApi by inject<UserApi>()

    override suspend fun getUserById(id: Int): User? = dbQuery {
        return@dbQuery userApi.getUserById(id)
    }

    override suspend fun deleteUserById(id: Int) = dbQuery {
        userApi.deleteUserById(id)
    }
}