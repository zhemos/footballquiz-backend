package com.zm.footballquiz.controllers.instrumentation

import com.zm.footballquiz.model.dto.CreateUserBody

object RegistrationControllerInstrumentation {

    fun givenValidCreateUserBody() = CreateUserBody(
        login = "login",
        email = "user@gmail.com",
        password = "root16",
        country = "by",
    )
}