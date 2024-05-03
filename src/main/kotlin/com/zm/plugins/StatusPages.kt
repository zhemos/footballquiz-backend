package com.zm.plugins

import com.zm.statuspages.ApplicationException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<ApplicationException.DataNotFound> { call, cause ->
            call.respond(cause.statusCode, cause.response)
        }
        exception<ApplicationException.Unknown> { call, cause ->
            call.respond(cause.statusCode, cause.response)
        }
        exception<ApplicationException.Unauthorized> { call, cause ->
            call.respond(cause.statusCode, cause.response)
        }
        exception<ApplicationException.BadRequest> { call, cause ->
            call.respond(cause.statusCode, cause.response)
        }
        exception<ApplicationException.Generic> { call, cause ->
            call.respond(cause.statusCode, cause.response)
        }
        exception<IllegalArgumentException> { call, _ ->
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}