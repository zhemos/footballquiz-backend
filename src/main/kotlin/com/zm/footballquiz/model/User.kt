package com.zm.footballquiz.model

import io.ktor.server.application.*
import io.ktor.server.auth.*

data class User(
    val id: Int,
    val login: String,
    val email: String,
    val password: String,
    val role: String,
    val nickname: String,
    val country: String,
) {

    sealed interface Role {
        val value: String

        data object SuperAdmin : Role {
            override val value: String get() = "super"
        }

        data object Admin : Role {
            override val value: String get() = "admin"
        }

        data object User : Role {
            override val value: String get() = "user"
        }

        data object Bot : Role {
            override val value: String get() = "bot"
        }
    }
}