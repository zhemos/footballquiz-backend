package com.zm.footballquiz.model

data class User(
    val id: Int,
    val login: String,
    val password: String,
    val role: String
) {

    sealed interface Role {
        val value: String
        val absoluteValue: String

        data object Admin : Role {
            override val value: String get() = ""
            override val absoluteValue: String get() = ""
        }

        data object User : Role {
            override val value: String get() = ""
            override val absoluteValue: String get() = ""
        }

        data object Bot : Role {
            override val value: String get() = ""
            override val absoluteValue: String get() = ""
        }
    }
}