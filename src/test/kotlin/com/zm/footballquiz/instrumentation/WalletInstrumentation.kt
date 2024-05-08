package com.zm.footballquiz.instrumentation

import com.zm.footballquiz.model.UpdateWallet
import com.zm.footballquiz.model.Wallet

object WalletInstrumentation {

    fun givenDefaultWallet(id: Int = 1) = Wallet(
        id = id,
        countOfCoins = 0,
        countOfTickets = 0,
        countOfEnergy = 0,
        countOfRedCards = 0,
        countOfWhistles = 0,
        countOfFans = 0,
    )

    fun givenFullWallet(id: Int = 1) = Wallet(
        id = id,
        countOfCoins = 1,
        countOfTickets = 1,
        countOfEnergy = 1,
        countOfRedCards = 1,
        countOfWhistles = 1,
        countOfFans = 1,
    )

    fun givenEmptyUpdateWallet(id: Int = 1) = UpdateWallet(
        id = id
    )

    fun givenFullUpdateWallet(id: Int = 1) = UpdateWallet(
        id = id,
        countOfCoins = 1,
        countOfTickets = 1,
        countOfEnergy = 1,
        countOfRedCards = 1,
        countOfWhistles = 1,
        countOfFans = 1,
    )
}