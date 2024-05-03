package com.zm.footballquiz.modules.user

import com.zm.footballquiz.model.User


interface UserController {

    suspend fun getUserById(id: Int): User?
}