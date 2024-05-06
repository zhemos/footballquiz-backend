package com.zm.footballquiz.routing.instrumentation

import com.zm.footballquiz.model.dto.CredentialsResponse
import com.zm.footballquiz.model.dto.LoginCredentialsBody

object AuthControllerInstrumentation {

    fun givenCredentialsResponse() = CredentialsResponse(
        accessToken = "accessToken",
        refreshToken = "refreshToken",
        expiresInMs = 100,
    )

    fun givenLoginCredentialsBody() = LoginCredentialsBody(
        login = "login",
        password = "password",
    )
}