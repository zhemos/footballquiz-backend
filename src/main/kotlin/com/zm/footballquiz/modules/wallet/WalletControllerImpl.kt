package com.zm.footballquiz.modules.wallet

import com.zm.footballquiz.api.wallet.WalletApi
import com.zm.footballquiz.model.UpdateWallet
import com.zm.footballquiz.model.dto.UpdateWalletBody
import com.zm.footballquiz.model.dto.WalletResponse
import com.zm.footballquiz.model.dto.toNewValue
import com.zm.footballquiz.modules.BaseController
import com.zm.footballquiz.statuspages.ApplicationException
import org.koin.core.component.inject

class WalletControllerImpl : BaseController(), WalletController {

    private val walletApi by inject<WalletApi>()

    override suspend fun updateWallet(
        id: Int,
        updateWalletBody: UpdateWalletBody
    ): WalletResponse? = dbQuery {
        val wallet = walletApi.getWalletById(id) ?: throw ApplicationException.DataNotFound
        val countOfCoins = updateWalletBody.countOfCoins?.toNewValue {
            return@toNewValue wallet.countOfCoins
        }
        val countOfTickets = updateWalletBody.countOfTickets?.toNewValue {
            return@toNewValue wallet.countOfTickets
        }
        val countOfEnergy = updateWalletBody.countOfEnergy?.toNewValue {
            return@toNewValue wallet.countOfEnergy
        }
        val countOfRedCards = updateWalletBody.countOfRedCards?.toNewValue {
            return@toNewValue wallet.countOfRedCards
        }
        val countOfWhistles = updateWalletBody.countOfWhistles?.toNewValue {
            return@toNewValue wallet.countOfWhistles
        }
        val countOfFans = updateWalletBody.countOfFans?.toNewValue {
            return@toNewValue wallet.countOfFans
        }
        val updateWallet = UpdateWallet(
            id = id,
            countOfCoins = countOfCoins,
            countOfTickets = countOfTickets,
            countOfEnergy = countOfEnergy,
            countOfRedCards = countOfRedCards,
            countOfWhistles = countOfWhistles,
            countOfFans = countOfFans,
        )
        return@dbQuery walletApi.updateWallet(updateWallet)?.toDto()
    }
}