package com.zm.footballquiz.instrumentation

import com.zm.footballquiz.model.UpdateWallet
import com.zm.footballquiz.model.Wallet
import com.zm.footballquiz.model.dto.UpdateSingleModeStatisticsBody
import com.zm.footballquiz.model.dto.UpdateBody
import com.zm.footballquiz.model.dto.UpdateWalletBody
import com.zm.footballquiz.model.dto.WalletResponse

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

    fun givenDefaultWalletResponse() = WalletResponse(
        countOfCoins = 0,
        countOfTickets = 0,
        countOfEnergy = 0,
        countOfRedCards = 0,
        countOfWhistles = 0,
        countOfFans = 0,
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

    fun givenUpdateWalletBodyOfTypeUnknownFull(
        value: Int = 10,
    ) = UpdateWalletBody(
        countOfCoins = UpdateBody(value, "unknown"),
        countOfTickets = UpdateBody(value, "unknown"),
        countOfEnergy = UpdateBody(value, "unknown"),
        countOfRedCards = UpdateBody(value, "unknown"),
        countOfWhistles = UpdateBody(value, "unknown"),
        countOfFans = UpdateBody(value, "unknown"),
    )

    fun givenUpdateWalletBodyOfTypeUnknownParticular(
        value: Int = 10,
    ) = UpdateWalletBody(
        countOfEnergy = UpdateBody(value, "unknown"),
        countOfRedCards = UpdateBody(value, "unknown"),
    )

    fun givenUpdateWalletBodyOfTypeUpdateFull(
        value: Int = 10,
    ) = UpdateWalletBody(
        countOfCoins = UpdateBody(value, "update"),
        countOfTickets = UpdateBody(value, "update"),
        countOfEnergy = UpdateBody(value, "update"),
        countOfRedCards = UpdateBody(value, "update"),
        countOfWhistles = UpdateBody(value, "update"),
        countOfFans = UpdateBody(value, "update"),
    )

    fun givenUpdateWalletBodyOfTypeAddFull(
        value: Int = 10,
    ) = UpdateWalletBody(
        countOfCoins = UpdateBody(value, "add"),
        countOfTickets = UpdateBody(value, "add"),
        countOfEnergy = UpdateBody(value, "add"),
        countOfRedCards = UpdateBody(value, "add"),
        countOfWhistles = UpdateBody(value, "add"),
        countOfFans = UpdateBody(value, "add"),
    )

    fun givenUpdateWalletBodyOfTypeRemoveFull(
        value: Int = 10,
    ) = UpdateWalletBody(
        countOfCoins = UpdateBody(value, "remove"),
        countOfTickets = UpdateBody(value, "remove"),
        countOfEnergy = UpdateBody(value, "remove"),
        countOfRedCards = UpdateBody(value, "remove"),
        countOfWhistles = UpdateBody(value, "remove"),
        countOfFans = UpdateBody(value, "remove"),
    )

    fun givenUpdateWalletBodyOfTypeMix(
        value: Int = 10,
    ) = UpdateWalletBody(
        countOfCoins = UpdateBody(value, "add"),
        countOfTickets = UpdateBody(value, "remove"),
        countOfEnergy = UpdateBody(value, "update"),
        countOfRedCards = UpdateBody(value, "remove"),
        countOfWhistles = UpdateBody(value, "add"),
        countOfFans = UpdateBody(value, "update"),
    )

    fun givenUpdateWalletBodyEmpty() = UpdateWalletBody()
}