package com.zm.statuspages

import com.zm.model.WrapperResponse
import io.ktor.http.*

sealed class ApplicationException(message: String) : Exception(message) {

    abstract val statusCode: HttpStatusCode
    abstract val code: Code
    abstract val response: WrapperResponse<Nothing>

    protected fun createResponse() = WrapperResponse(code = code.value, data = null, error = message)

    data object UnauthorizedException : ApplicationException(message = "token exception") {
        override val statusCode: HttpStatusCode get() = HttpStatusCode.Unauthorized
        override val code: Code get() = Code.UNAUTHORIZED
        override val response: WrapperResponse<Nothing> get() = createResponse()
    }

    data object RefreshTokenException : ApplicationException(message = "refresh token exception") {
        override val statusCode: HttpStatusCode get() = HttpStatusCode.BadRequest
        override val code: Code get() = Code.DECLINE
        override val response: WrapperResponse<Nothing> get() = createResponse()
    }

    data object DataNotFound : ApplicationException(message = "Data not found") {
        override val statusCode: HttpStatusCode get() = HttpStatusCode.BadRequest
        override val code: Code get() = Code.DATA_NOT_FOUND
        override val response: WrapperResponse<Nothing> get() = createResponse()
    }
}

enum class Code(val value: Int) {
    SUCCESS(200),
    DECLINE(400),
    UNAUTHORIZED(401),
    DATA_NOT_FOUND(404)
}