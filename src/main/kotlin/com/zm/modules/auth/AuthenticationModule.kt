package com.zm.modules.auth

import com.auth0.jwt.interfaces.JWTVerifier
import com.zm.api.user.UserApi
import com.zm.config.JwtConfig
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun Application.authenticationModule(
    userApi: UserApi,
    jwtConfig: JwtConfig,
    tokenVerifier: JWTVerifier
) {
    install(Authentication) {
        jwt {
            realm = jwtConfig.realm
            verifier(tokenVerifier)
            validate {
                kotlin.run {
                    val login = it.payload.claims["login"]?.asString() ?: ""
                    val ati = it.payload.claims["ati"]?.asString() ?: ""
                    if (login.isNotEmpty() && ati.isEmpty()) {
                        JWTPrincipal(it.payload)
                    } else {
                        null
                    }
                }
            }
            challenge {_, _ ->
                call.respond(
                    HttpStatusCode.Unauthorized,
                    "Token is not valid or has expired"
                )
            }
        }
    }
}