package com.zm.footballquiz.modules.user

import com.zm.footballquiz.api.user.UserApi
import com.zm.footballquiz.model.User
import com.zm.footballquiz.model.dto.CreateUserBody
import com.zm.footballquiz.model.dto.UserResponse
import com.zm.footballquiz.model.dto.UserUpdateBody
import com.zm.footballquiz.modules.BaseController
import com.zm.footballquiz.statuspages.ApplicationException
import com.zm.footballquiz.util.isEmailValid
import com.zm.footballquiz.util.isPasswordValid
import org.koin.core.component.inject

class UserControllerImpl : BaseController(), UserController {

    private val userApi by inject<UserApi>()

    override suspend fun createUser(
        createUserBody: CreateUserBody,
        userRole: User.Role
    ): Unit = with(createUserBody) {
        if (email.isEmailValid().not()) {
            throw ApplicationException.Generic("invalid email")
        }
        if (password.isPasswordValid().not()) {
            throw ApplicationException.Generic("invalid password")
        }
        dbQuery {
            userApi.getUserByLoginOrEmail(login, email)?.let {
                throw ApplicationException.Generic("user was already created")
            }
            userApi.createUser(this, userRole)
        }
    }

    override suspend fun deleteUserById(userId: Int): Int = dbQuery {
        val user = userApi.getUserById(userId) ?: throw ApplicationException.DataNotFound
        if (user.role != User.Role.User.value) {
            throw ApplicationException.Generic("access denied")
        }
        return@dbQuery userApi.deleteUserById(userId)
    }

    override suspend fun deleteAdminById(userId: Int): Int = dbQuery {
        val user = userApi.getUserById(userId) ?: throw ApplicationException.DataNotFound
        if (user.role != User.Role.Admin.value) {
            throw ApplicationException.Generic("access denied")
        }
        return@dbQuery userApi.deleteUserById(userId)
    }

    override suspend fun getUsers(userId: Int): List<UserResponse> = dbQuery {
        return@dbQuery userApi.getUsers().map {
            it.toDto()
        }.filter { user ->
            user.id != userId && user.role != User.Role.SuperAdmin.value
        }
    }

    override suspend fun getUserById(userId: Int): UserResponse? = dbQuery {
        return@dbQuery userApi.getUserById(userId)?.toDto()
    }

    override suspend fun updateUser(userId: Int, userUpdateBody: UserUpdateBody): UserResponse? = dbQuery {
        return@dbQuery userApi.updateUser(userId, userUpdateBody)?.toDto()
    }
}