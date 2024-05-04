package com.zm.footballquiz.modules.user

import com.zm.footballquiz.model.User
import com.zm.footballquiz.model.dto.CreateUserBody
import com.zm.footballquiz.model.dto.UserResponse
import com.zm.footballquiz.model.dto.UserUpdateBody
import com.zm.footballquiz.modules.*
import com.zm.footballquiz.statuspages.ApplicationException
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.userModule() {

    val controller by inject<UserController>()

    route("user") {

        post("create") {
            checkAdminPermission {
                receive<CreateUserBody> { createUserBody ->
                    controller.createUser(
                        createUserBody = createUserBody,
                        userRole = User.Role.User,
                    )
                    call.respond(successResult(null))
                }
            }
        }

        post("create/admin") {
            checkSuperAdminPermission {
                receive<CreateUserBody> { createUserBody ->
                    controller.createUser(
                        createUserBody = createUserBody,
                        userRole = User.Role.Admin,
                    )
                    call.respond(successResult(null))
                }
            }
        }

        post("remove") {
            fetchUser { userId ->
                controller.deleteUserById(userId)
                call.respond(successResult(null))
            }
        }

        post("remove/{id}") {
            checkAdminPermission {
                val userId = call.parameters["id"]?.toInt() ?: throw ApplicationException.BadRequest()
                controller.deleteUserById(userId)
                call.respond(successResult(null))
            }
        }

        post("remove/admin/{id}") {
            checkSuperAdminPermission {
                val userId = call.parameters["id"]?.toInt() ?: throw ApplicationException.BadRequest()
                controller.deleteAdminById(userId)
                call.respond(successResult(null))
            }
        }

        get("") {
            fetchUser { userId ->
                val user: UserResponse? = controller.getUserById(userId)
                call.respond(successResult(user))
            }
        }

        get("all") {
            checkAdminPermission {
                fetchUser { userId ->
                    val users = controller.getUsers(userId)
                    call.respond(successResult(users))
                }
            }
        }

        post("update") {
            fetchUser { userId ->
                receive<UserUpdateBody> { userUpdateBody ->
                    val user = controller.updateUser(
                        userId = userId,
                        userUpdateBody = userUpdateBody
                    ) ?: throw ApplicationException.DataNotFound
                    call.respond(successResult(user))
                }
            }
        }

        post("update/{id}") {
            checkSuperAdminPermission {
                val userId = call.parameters["id"]?.toInt() ?: throw ApplicationException.BadRequest()
                receive<UserUpdateBody> { userUpdateBody ->
                    val user = controller.updateUser(
                        userId = userId,
                        userUpdateBody = userUpdateBody
                    ) ?: throw ApplicationException.DataNotFound
                    call.respond(successResult(user))
                }
            }
        }
    }
}