package com.zm.footballquiz.model.dto

data class UpdateWalletBody(
    val countOfCoins: UpdateBody? = null,
    val countOfTickets: UpdateBody? = null,
    val countOfEnergy: UpdateBody? = null,
    val countOfRedCards: UpdateBody? = null,
    val countOfWhistles: UpdateBody? = null,
    val countOfFans: UpdateBody? = null,
)

data class WalletResponse(
    val countOfCoins: Int,
    val countOfTickets: Int,
    val countOfEnergy: Int,
    val countOfRedCards: Int,
    val countOfWhistles: Int,
    val countOfFans: Int,
)