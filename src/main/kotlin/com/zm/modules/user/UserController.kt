package com.zm.modules.user

import com.zm.model.User

interface UserController {

    suspend fun getUserById(id: Int): User?
}