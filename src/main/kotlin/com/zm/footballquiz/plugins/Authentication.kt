package com.zm.footballquiz.plugins

import com.auth0.jwt.interfaces.JWTVerifier
import com.zm.footballquiz.api.user.UserApi
import com.zm.footballquiz.config.JwtConfig
import com.zm.footballquiz.modules.WrapperResponse
import com.zm.footballquiz.statuspages.ApplicationException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun Application.configureAuthentication(
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
                    WrapperResponse(
                        code = ApplicationException.Code.UNAUTHORIZED.value,
                        data = null,
                        error = "Token is not valid or has expired"
                    )
                )
            }
        }
    }
}