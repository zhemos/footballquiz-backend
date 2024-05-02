package com.zm.api.user

import com.zm.model.CreateUserBody
import com.zm.model.User

interface UserApi {

    fun createUser(createUserBody: CreateUserBody): User

    fun getUserById(id: Int): User?

    fun getUserByLogin(login: String): User?

    fun getUserByLoginOrEmail(login: String, email: String): User?
}