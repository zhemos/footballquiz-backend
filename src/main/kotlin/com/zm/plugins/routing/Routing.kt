package com.zm.plugins.routing

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        route("api/admin") {
            configureRoutingAdmin()
        }
        route("api/user") {
            configureRoutingUser()
        }
    }
}
