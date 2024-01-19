package com.zm.statuspages

import com.zm.model.WrapperResponse
import io.ktor.http.*


sealed class ApplicationException(message: String) : Exception(message) {

    abstract val statusCode: HttpStatusCode
    abstract val code: Code
    val response: WrapperResponse<Nothing> = createResponse()

    private fun createResponse() = WrapperResponse(code = code.value, data = null, error = message)

    data object Unauthorized : ApplicationException(message = "token exception") {
        private fun readResolve(): Any = Unauthorized
        override val statusCode: HttpStatusCode get() = HttpStatusCode.Unauthorized
        override val code: Code get() = Code.UNAUTHORIZED
    }

    sealed class Register(message: String) : ApplicationException(message = message) {
        override val statusCode: HttpStatusCode get() = HttpStatusCode.OK
        override val code: Code get() = Code.SUCCESS

        data object IncorrectLogin : Register(message = "Login is incorrect") {
            private fun readResolve(): Any = UserWasAlreadyCreated
        }

        data object IncorrectEmail : Register(message = "Email is incorrect") {
            private fun readResolve(): Any = UserWasAlreadyCreated
        }

        data object IncorrectPassword : Register(message = "Password is incorrect") {
            private fun readResolve(): Any = UserWasAlreadyCreated
        }

        data object IncorrectDateOfBirth : Register(message = "Date of birthday is incorrect") {
            private fun readResolve(): Any = UserWasAlreadyCreated
        }

        data object CountryIsEmpty : Register(message = "Country is empty") {
            private fun readResolve(): Any = UserWasAlreadyCreated
        }

        data object UserWasAlreadyCreated : Register(message = "User was already created") {
            private fun readResolve(): Any = UserWasAlreadyCreated
        }
    }

    data object RefreshToken : ApplicationException(message = "refresh token exception") {
        override val statusCode: HttpStatusCode get() = HttpStatusCode.BadRequest
        override val code: Code get() = Code.DECLINE
    }

    data object DataNotFound : ApplicationException(message = "Data not found") {
        override val statusCode: HttpStatusCode get() = HttpStatusCode.BadRequest
        override val code: Code get() = Code.DATA_NOT_FOUND
    }

    data object Unknown : ApplicationException(message = "Internal server error") {
        override val statusCode: HttpStatusCode get() = HttpStatusCode.InternalServerError
        override val code: Code get() = Code.SERVER_ERROR
    }
}

enum class Code(val value: Int) {
    SUCCESS(200),
    DECLINE(400),
    UNAUTHORIZED(401),
    DATA_NOT_FOUND(404),
    SERVER_ERROR(500)
}