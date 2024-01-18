package com.zm.modules.user

import com.zm.model.successResult
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
            principal?.payload?.getClaim("id")?.asInt()?.let { userId ->
                val user = controller.getUserById(userId)
                call.respond(successResult(user))
            }
        }
    }
}