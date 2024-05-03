package com.zm.footballquiz.statuspages

import com.zm.footballquiz.modules.WrapperResponse
import io.ktor.http.*

sealed class ApplicationException(message: String) : Exception(message) {

    abstract val statusCode: HttpStatusCode
    abstract val code: Code
    val response: WrapperResponse<Nothing> = createResponse()

    private fun createResponse() = WrapperResponse(code = code.value, data = null, error = message)

    data object DataNotFound : ApplicationException(message = "Data not found") {
        override val statusCode: HttpStatusCode get() = HttpStatusCode.BadRequest
        override val code: Code get() = Code.DATA_NOT_FOUND
    }

    data object Unknown : ApplicationException(message = "Internal server error") {
        override val statusCode: HttpStatusCode get() = HttpStatusCode.InternalServerError
        override val code: Code get() = Code.SERVER_ERROR
    }

    data object Unauthorized : ApplicationException(message = "token exception") {
        override val statusCode: HttpStatusCode get() = HttpStatusCode.Unauthorized
        override val code: Code get() = Code.UNAUTHORIZED
    }

    class BadRequest(message: String = "bad request") : ApplicationException(message = message) {
        override val statusCode: HttpStatusCode get() = HttpStatusCode.BadRequest
        override val code: Code get() = Code.DECLINE
    }

    class Generic(message: String) : ApplicationException(message = message) {
        override val statusCode: HttpStatusCode get() = HttpStatusCode.OK
        override val code: Code get() = Code.DECLINE
    }

    enum class Code(val value: Int) {
        SUCCESS(200),
        DECLINE(400),
        UNAUTHORIZED(401),
        DATA_NOT_FOUND(404),
        SERVER_ERROR(500)
    }
}