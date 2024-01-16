package com.zm.api.user

import com.zm.model.User
import org.koin.core.component.KoinComponent

object UserApiImpl : UserApi, KoinComponent {

    private val users = listOf(
        User(1, "root", "root", ""),
        User(2, "root16", "root", ""),
        User(3, "root32", "root", "")
    )

    override fun getUserById(id: Int): User? {
        return users.find { it.id == id }
    }

    override fun getUserByLogin(login: String): User? {
        return users.find { it.login == login }
    }
}