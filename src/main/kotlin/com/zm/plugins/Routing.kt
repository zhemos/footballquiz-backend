package com.zm.plugins

import com.zm.modules.auth.authModule
import com.zm.modules.user.userModule
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        route("api") {
            authModule()
            authenticate {
                userModule()
            }
        }
    }
}
