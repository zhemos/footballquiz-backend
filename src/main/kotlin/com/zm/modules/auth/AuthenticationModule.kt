package com.zm.modules.auth

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun AuthenticationConfig.authenticationModule() {
    jwt("jwt") {

    }
}