package com.zm.footballquiz.modules.auth

import com.zm.footballquiz.model.User
import com.zm.footballquiz.model.dto.CreateUserBody
import com.zm.footballquiz.model.dto.LoginCredentialsBody
import com.zm.footballquiz.model.dto.RefreshBody
import com.zm.footballquiz.modules.receive
import com.zm.footballquiz.modules.successResult
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.authModule() {

    val controller by inject<AuthController>()

    route("auth") {

        post("register/user") {
            receive<CreateUserBody> { createUserBody ->
                val credentialsResponse = controller.createUser(
                    createUserBody = createUserBody,
                    userRole = User.Role.User,
                )
                call.respond(successResult(credentialsResponse))
            }
        }

        post("login") {
            receive<LoginCredentialsBody> { loginCredentials ->
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