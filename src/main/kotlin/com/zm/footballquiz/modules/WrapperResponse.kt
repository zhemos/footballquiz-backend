package com.zm.footballquiz.modules

import com.zm.footballquiz.statuspages.ApplicationException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*

data class WrapperResponse<T>(
    val code: Int,
    val data: T?,
    val error: String?
)

inline fun PipelineContext<Unit, ApplicationCall>.checkAuthorize(
    foundUser: (userId: Int) -> Unit
) {
    val principal = call.principal<JWTPrincipal>()
    principal?.payload?.getClaim("id")?.asInt()?.let { userId ->
        foundUser(userId)
    }
}

suspend inline fun <reified T : Any> PipelineContext<Unit, ApplicationCall>.receive(
    successReceive: (T) -> Unit
) {
    try {
        val receive = call.receive<T>()
        successReceive(receive)
    } catch (e: CannotTransformContentToTypeException) {
        val httpStatusCode = HttpStatusCode.BadRequest
        call.respond(
            status = httpStatusCode,
            message = WrapperResponse(httpStatusCode.value, null, httpStatusCode.description)
        )
    }
}

fun <T> successResult(data: T): WrapperResponse<T> = WrapperResponse(
    code = ApplicationException.Code.SUCCESS.value,
    data = data,
    error = null
)