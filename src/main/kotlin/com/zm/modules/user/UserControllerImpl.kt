package com.zm.modules.user

import com.zm.api.user.UserApi
import com.zm.model.User
import com.zm.modules.BaseController
import org.koin.core.component.inject

class UserControllerImpl : BaseController(), UserController {

    private val userApi by inject<UserApi>()

    override suspend fun getUserById(id: Int): User? = dbQuery {
        return@dbQuery userApi.getUserById(id)
    }
}