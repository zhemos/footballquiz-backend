package com.zm.footballquiz.modules.user

import com.zm.footballquiz.model.User
import com.zm.footballquiz.model.dto.CreateUserBody

interface UserController {

    suspend fun createUser(createUserBody: CreateUserBody)

    suspend fun getUserById(id: Int): User?

    suspend fun deleteUserById(id: Int)
}