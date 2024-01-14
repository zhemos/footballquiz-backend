package com.zm.plugins.routing

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.configureRoutingUser() {
    get("/") {
        call.respondText("Hello User!")
    }
}