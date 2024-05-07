package com.zm.footballquiz.api.user

import com.zm.footballquiz.model.dto.CreateUserBody
import com.zm.footballquiz.model.User
import com.zm.footballquiz.model.dto.UserUpdateBody

interface UserApi {

    fun createUser(createUserBody: CreateUserBody, userRole: User.Role): User

    fun getUsers(): List<User>

    fun getUserById(id: Int): User?

    fun getUserByLogin(login: String): User?

    fun getUserByLoginOrEmail(login: String, email: String): User?

    fun deleteUserById(userId: Int): Int

    fun updateUser(userId: Int, userUpdateBody: UserUpdateBody): User?
}