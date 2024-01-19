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
    exception<ApplicationException.Register.IncorrectLogin> { call, cause ->
        call.respond(cause.statusCode, cause.response)
    }
    exception<ApplicationException.Register.IncorrectEmail> { call, cause ->
        call.respond(cause.statusCode, cause.response)
    }
    exception<ApplicationException.Register.IncorrectPassword> { call, cause ->
        call.respond(cause.statusCode, cause.response)
    }
    exception<ApplicationException.Register.IncorrectDateOfBirth> { call, cause ->
        call.respond(cause.statusCode, cause.response)
    }
    exception<ApplicationException.Register.CountryIsEmpty> { call, cause ->
        call.respond(cause.statusCode, cause.response)
    }
    exception<ApplicationException.Register.UserWasAlreadyCreated> { call, cause ->
        call.respond(cause.statusCode, cause.response)
    }
}