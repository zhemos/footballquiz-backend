package com.zm.util

import org.mindrot.jbcrypt.BCrypt

object PasswordManager : PasswordManagerContract {

    private const val MIN_LENGTH_PASSWORD = 6
    private val letters = 'a'..'z'
    private val uppercaseLetters = 'A'..'Z'
    private val numbers = '0'..'9'

    override fun validatePassword(attempt: String, userPassword: String): Boolean {
        return BCrypt.checkpw(attempt, userPassword)
    }

    override fun encryptPassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    override fun generatePasswordWithDefault(): String {
        TODO("Not yet implemented")
    }

    fun isValidPassword(password: String): Boolean {
        if (password.length < MIN_LENGTH_PASSWORD) return false
        val stringWithoutAccessSymbols = password.filter {
            letters.contains(it).not() && uppercaseLetters.contains(it).not() && numbers.contains(it).not()
        }
        return stringWithoutAccessSymbols.isEmpty()
    }
}

interface PasswordManagerContract {
    fun validatePassword(attempt: String, userPassword: String): Boolean
    fun encryptPassword(password: String): String
    fun generatePasswordWithDefault(): String
}