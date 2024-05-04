package com.zm.footballquiz.modules.user

import com.zm.footballquiz.model.User
import com.zm.footballquiz.model.dto.CreateUserBody
import com.zm.footballquiz.model.dto.UserResponse
import com.zm.footballquiz.model.dto.UserUpdateBody

interface UserController {

    suspend fun createUser(createUserBody: CreateUserBody, userRole: User.Role)

    suspend fun deleteUserById(userId: Int)

    suspend fun deleteAdminById(userId: Int)

    suspend fun getUsers(userId: Int): List<UserResponse>

    suspend fun getUserById(userId: Int): UserResponse?

    suspend fun updateUser(userId: Int, userUpdateBody: UserUpdateBody): UserResponse?
}