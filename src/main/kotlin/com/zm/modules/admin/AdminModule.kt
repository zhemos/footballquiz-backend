package com.zm.modules.admin

import com.zm.model.PostAdminBody
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.adminModule() {

    val controller by inject<AdminController>()

    post("login") {
        val postAdmin = call.receive<PostAdminBody>()
        println("$controller")
        call.respondText(postAdmin.login)
    }
}