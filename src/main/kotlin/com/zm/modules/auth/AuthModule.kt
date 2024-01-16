package com.zm.modules.auth

import com.zm.model.LoginCredentials
import com.zm.model.RefreshBody
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.authModule() {

    val controller by inject<AuthController>()

    route("auth") {
        post("login") {
            val loginCredentials = call.receive<LoginCredentials>()
            val credentialsResponse = controller.login(loginCredentials)
            call.respond(credentialsResponse)
        }

        post("refresh") {
            val refreshBody = call.receive<RefreshBody>()
            val credentialsResponse = controller.refreshToken(refreshBody)
            call.respond(credentialsResponse)
        }
    }
}