package com.zm.footballquiz.modules.user

import com.zm.footballquiz.model.dto.CreateUserBody
import com.zm.footballquiz.model.dto.UserResponse
import com.zm.footballquiz.modules.fetchUser
import com.zm.footballquiz.modules.receive
import com.zm.footballquiz.modules.successResult
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.userModule() {

    val controller by inject<UserController>()

    route("user") {

        get {
            fetchUser { userId ->
                val user: UserResponse? = controller.getUserById(userId)?.toDto()
                call.respond(successResult(user))
            }
        }

        post("create") {
            receive<CreateUserBody> { createUserBody ->
                controller.createUser(createUserBody)
                call.respond(successResult(null))
            }
        }

        post("delete") {
            fetchUser { userId ->
                controller.deleteUserById(userId)
                call.respond(successResult(null))
            }
        }

        get("{test}") {
            call.respond(successResult(call.parameters["test"]))
        }
        
        //create users
        //create admins
        //delete users by id
        //delete admins by id
        //get all users except super admin
        //get all users except super admin and admins
        //update users
        //update users by id
        //update admin
        //update admin by id
    }
}