package com.zm.footballquiz.routing.instrumentation

import com.zm.footballquiz.model.dto.CredentialsResponse

object RegistrationControllerInstrumentation {

    fun givenCredentialsResponse() = CredentialsResponse(
        accessToken = "accessToken",
        refreshToken = "refreshToken",
        expiresInMs = 100,
    )
}