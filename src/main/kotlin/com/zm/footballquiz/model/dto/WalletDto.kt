package com.zm.footballquiz.model.dto

data class UpdateWalletBody(
    val countOfCoins: Int? = null,
    val countOfTickets: Int? = null,
    val countOfEnergy: Int? = null,
    val countOfRedCards: Int? = null,
    val countOfWhistles: Int? = null,
    val countOfFans: Int? = null,
)

data class WalletResponse(
    val countOfCoins: Int,
    val countOfTickets: Int,
    val countOfEnergy: Int,
    val countOfRedCards: Int,
    val countOfWhistles: Int,
    val countOfFans: Int,
)