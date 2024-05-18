package com.zm.footballquiz.plugins

import com.zm.footballquiz.modules.auth.authModule
import com.zm.footballquiz.modules.category.categoryModule
import com.zm.footballquiz.modules.statistics.statisticsModule
import com.zm.footballquiz.modules.user.userModule
import com.zm.footballquiz.modules.wallet.walletModule
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        route("api") {
            authModule()
            authenticate {
                userModule()
                statisticsModule()
                walletModule()
                categoryModule()
            }
        }
    }
}
