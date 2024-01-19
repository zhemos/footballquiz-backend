package com.zm.statuspages

import com.zm.model.WrapperResponse
import io.ktor.http.*

sealed class ApplicationException(message: String) : Exception(message) {

    abstract val statusCode: HttpStatusCode
    abstract val code: Code
    val response: WrapperResponse<Nothing> = createResponse()

    protected fun createResponse() = WrapperResponse(code = code.value, data = null, error = message)

    data object Unauthorized : ApplicationException(message = "token exception") {
        override val statusCode: HttpStatusCode get() = HttpStatusCode.Unauthorized
        override val code: Code get() = Code.UNAUTHORIZED
    }

    data object UserWasAlreadyCreated : ApplicationException(message = "User was already created") {
        override val statusCode: HttpStatusCode get() = HttpStatusCode.OK
        override val code: Code get() = Code.SUCCESS
    }

    data object RefreshToken : ApplicationException(message = "refresh token exception") {
        override val statusCode: HttpStatusCode get() = HttpStatusCode.BadRequest
        override val code: Code get() = Code.DECLINE
    }

    data object DataNotFound : ApplicationException(message = "Data not found") {
        override val statusCode: HttpStatusCode get() = HttpStatusCode.BadRequest
        override val code: Code get() = Code.DATA_NOT_FOUND
    }
}

enum class Code(val value: Int) {
    SUCCESS(200),
    DECLINE(400),
    UNAUTHORIZED(401),
    DATA_NOT_FOUND(404)
}