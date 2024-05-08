package com.zm.footballquiz.modules.wallet

import com.zm.footballquiz.model.Wallet
import com.zm.footballquiz.model.dto.WalletResponse

fun Wallet.toDto(): WalletResponse {
    return WalletResponse(
        countOfCoins = countOfCoins,
        countOfTickets = countOfTickets,
        countOfEnergy = countOfEnergy,
        countOfRedCards = countOfRedCards,
        countOfWhistles = countOfWhistles,
        countOfFans = countOfFans,
    )
}