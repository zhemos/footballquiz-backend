package com.zm.statuspages

import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun StatusPagesConfig.authStatusPages() {
    exception<ApplicationException.Unauthorized> { call, cause ->
        call.respond(cause.statusCode, cause.response)
    }
    exception<ApplicationException.RefreshToken> { call, cause ->
        call.respond(cause.statusCode, cause.response)
    }
}