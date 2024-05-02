package com.zm.util

object EmailManager {
    private const val REGEX_EMAIL = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"

    fun isValidEmail(email: String): Boolean {
        return email.matches(REGEX_EMAIL.toRegex())
    }
}