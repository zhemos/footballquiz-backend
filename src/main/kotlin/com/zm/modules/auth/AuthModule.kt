package com.zm.modules.auth

import com.zm.model.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.authModule() {

    val controller by inject<AuthController>()

    route("auth") {

        post("register/admin") {
            receive<CreateUserBody> { createUserBody ->
                val credentialsResponse = controller.createUser(createUserBody)
                call.respond(successResult(credentialsResponse))
            }
        }

        post("register/user") {
            receive<CreateUserBody> { createUserBody ->
                println(createUserBody)
            }
        }

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