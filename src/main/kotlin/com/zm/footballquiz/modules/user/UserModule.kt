package com.zm.footballquiz.modules.user

import com.zm.footballquiz.model.dto.UserResponse
import com.zm.footballquiz.modules.checkAuthorize
import com.zm.footballquiz.modules.successResult
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.userModule() {

    val controller by inject<UserController>()

    route("user") {

        get {
            checkAuthorize { userId ->
                val user: UserResponse? = controller.getUserById(userId)?.toDto()
                call.respond(successResult(user))
            }
        }

        post("delete") {
            checkAuthorize { userId ->
                controller.deleteUserById(userId)
                call.respond(successResult(null))
            }
        }
    }
}