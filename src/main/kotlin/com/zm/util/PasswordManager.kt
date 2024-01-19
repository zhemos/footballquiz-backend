package com.zm.util

import org.mindrot.jbcrypt.BCrypt

object PasswordManager : PasswordManagerContract {

    override fun validatePassword(attempt: String, userPassword: String): Boolean {
        return BCrypt.checkpw(attempt, userPassword)
    }

    override fun encryptPassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    override fun generatePasswordWithDefault(): String {
        TODO("Not yet implemented")
    }
}

interface PasswordManagerContract {
    fun validatePassword(attempt: String, userPassword: String): Boolean
    fun encryptPassword(password: String): String
    fun generatePasswordWithDefault(): String
}