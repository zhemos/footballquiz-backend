package com.zm.plugins.routing

import com.zm.modules.admin.adminModule
import io.ktor.server.routing.*

fun Route.configureRoutingAdmin() {
    adminModule()
}