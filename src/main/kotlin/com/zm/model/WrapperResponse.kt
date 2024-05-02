package com.zm.model

import com.zm.statuspages.Code
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*

data class WrapperResponse<T>(
    val code: Int,
    val data: T?,
    val error: String?
)

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
    code = Code.SUCCESS.value,
    data = data,
    error = null
)