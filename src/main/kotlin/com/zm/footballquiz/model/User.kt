package com.zm.footballquiz.model

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