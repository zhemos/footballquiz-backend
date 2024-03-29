package com.zm.plugins

import com.zm.statuspages.ApplicationException
import com.zm.statuspages.authStatusPages
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        authStatusPages()
        exception<ApplicationException.DataNotFound> { call, cause ->
            call.respond(cause.statusCode, cause.response)
        }
        exception<ApplicationException.Unknown> { call, cause ->
            call.respond(cause.statusCode, cause.response)
        }
        exception<IllegalArgumentException> { call, _ ->
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}