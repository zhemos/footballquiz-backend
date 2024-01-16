package com.zm.modules.user

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.userModule() {

    val controller by inject<UserController>()

    route("user") {
        get {
            val principal = call.principal<JWTPrincipal>()
            val login = principal?.payload?.getClaim("login")?.asString()
            call.respondText("$login")
        }
    }
}