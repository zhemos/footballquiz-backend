package com.zm.footballquiz.modules.wallet

import com.zm.footballquiz.model.dto.UpdateWalletBody
import com.zm.footballquiz.modules.checkAdminPermission
import com.zm.footballquiz.modules.fetchWallet
import com.zm.footballquiz.modules.receive
import com.zm.footballquiz.modules.successResult
import com.zm.footballquiz.statuspages.ApplicationException
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.walletModule() {

    val controller by inject<WalletController>()

    route("wallet") {

        post("update") {
            receive<UpdateWalletBody> { updateWalletBody ->
                fetchWallet { walletId ->
                    val wallet = controller.updateWallet(walletId, updateWalletBody)
                    call.respond(successResult(wallet))
                }
            }
        }

        post("update/{id}") {
            val walletId = call.parameters["id"]?.toInt() ?: throw ApplicationException.BadRequest()
            receive<UpdateWalletBody> { updateWalletBody ->
                checkAdminPermission {
                    val wallet = controller.updateWallet(walletId, updateWalletBody)
                    call.respond(successResult(wallet))
                }
            }
        }
    }
}