package com.zm.footballquiz.instrumentation

import com.zm.footballquiz.model.dto.CreateUserBody
import com.zm.footballquiz.model.dto.CredentialsResponse
import com.zm.footballquiz.model.dto.LoginCredentialsBody

object AuthInstrumentation {

    fun givenCredentialsResponse() = CredentialsResponse(
        accessToken = "accessToken",
        refreshToken = "refreshToken",
        expiresInMs = 100,
    )

    fun givenLoginCredentialsBody() = LoginCredentialsBody(
        login = "login",
        password = "password",
    )

    fun givenValidCreateUserBody() = CreateUserBody(
        login = "login",
        email = "user@gmail.com",
        password = "root16",
        country = "by",
    )
}