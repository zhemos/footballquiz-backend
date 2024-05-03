package com.zm.footballquiz.modules.user

import com.zm.footballquiz.api.user.UserApi
import com.zm.footballquiz.model.User
import com.zm.footballquiz.model.dto.CreateUserBody
import com.zm.footballquiz.modules.BaseController
import com.zm.footballquiz.statuspages.ApplicationException
import com.zm.footballquiz.util.isEmailValid
import com.zm.footballquiz.util.isPasswordValid
import org.koin.core.component.inject

class UserControllerImpl : BaseController(), UserController {

    private val userApi by inject<UserApi>()

    override suspend fun createUser(createUserBody: CreateUserBody) = with(createUserBody) {
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
            userApi.createUser(this)
        }
    }

    override suspend fun getUserById(id: Int): User? = dbQuery {
        return@dbQuery userApi.getUserById(id)
    }

    override suspend fun deleteUserById(id: Int) = dbQuery {
        userApi.deleteUserById(id)
    }
}