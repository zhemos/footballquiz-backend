package com.zm.modules.auth

import com.zm.model.LoginCredentials
import com.zm.model.RefreshBody
import com.zm.model.receive
import com.zm.model.successResult
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.authModule() {

    val controller by inject<AuthController>()

    route("auth") {
        post("login") {
            receive<LoginCredentials> { loginCredentials ->
                val credentialsResponse = controller.login(loginCredentials)
                call.respond(successResult(credentialsResponse))
            }
        }

        post("refresh") {
            receive<RefreshBody> { refreshBody ->
                val credentialsResponse = controller.refreshToken(refreshBody)
                call.respond(successResult(credentialsResponse))
            }
        }
    }
}