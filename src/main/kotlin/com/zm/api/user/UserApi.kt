package com.zm.api.user

import com.zm.model.User

interface UserApi {

    fun getUserById(id: Int): User?

    fun getUserByLogin(login: String): User?
}