package com.zm.footballquiz.model.dto

data class WalletResponse(
    val countOfCoins: Int,
    val countOfTickets: Int,
    val countOfEnergy: Int,
    val countOfRedCards: Int,
    val countOfWhistles: Int,
    val countOfFans: Int,
)