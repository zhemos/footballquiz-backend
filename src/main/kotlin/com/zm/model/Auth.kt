package com.zm.model

import com.zm.statuspages.ApplicationException
import com.zm.util.DateManagerContract
import com.zm.util.EmailManager
import com.zm.util.PasswordManager

data class CreateUserBody(
    val login: String,
    val email: String,
    val password: String,
    val dateOfBirth: String,
    val country: String
) {
    fun getRegisterExceptionOrNull(dateManager: DateManagerContract): ApplicationException.Register? {
        if (login.length < MIN_LENGTH_LOGIN) {
            return ApplicationException.Register.IncorrectLogin
        }
        if (PasswordManager.isValidPassword(password).not()) {
            return ApplicationException.Register.IncorrectPassword
        }
        if (EmailManager.isValidEmail(email).not()) {
            return ApplicationException.Register.IncorrectEmail
        }
        if (dateManager.convertStringToCalendar(dateOfBirth) == null) {
            return ApplicationException.Register.IncorrectDateOfBirth
        }
        if (country.isEmpty()) {
            return ApplicationException.Register.CountryIsEmpty
        }
        return null
    }
}
private const val MIN_LENGTH_LOGIN = 6
private const val MIN_YEAR_OLD = 18

data class LoginCredentials(
    val login: String,
    val password: String
)

data class RefreshBody(
    val refreshToken: String
)

data class CredentialsResponse(
    val accessToken: String,
    val refreshToken: String,
    val expiresInMs: Long
)