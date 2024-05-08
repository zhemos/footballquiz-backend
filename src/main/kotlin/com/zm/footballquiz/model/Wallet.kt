package com.zm.footballquiz.model

data class Wallet(
    val id: Int,
    val countOfCoins: Int,
    val countOfTickets: Int,
    val countOfEnergy: Int,
    val countOfRedCards: Int,
    val countOfWhistles: Int,
    val countOfFans: Int,
)

data class UpdateWallet(
    val id: Int,
    val countOfCoins: Int? = null,
    val countOfTickets: Int? = null,
    val countOfEnergy: Int? = null,
    val countOfRedCards: Int? = null,
    val countOfWhistles: Int? = null,
    val countOfFans: Int? = null,
)