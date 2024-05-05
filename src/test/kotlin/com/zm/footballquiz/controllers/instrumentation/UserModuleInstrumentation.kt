package com.zm.footballquiz.controllers.instrumentation

import com.zm.footballquiz.model.User

object UserModuleInstrumentation {

    fun givenUser(id: Int = 123): User {
        return User(
            id = id,
            login = "login",
            email = "user@gmail.com",
            password = "root16",
            role = "user",
            nickname = "",
            country = "by",
        )
    }
}