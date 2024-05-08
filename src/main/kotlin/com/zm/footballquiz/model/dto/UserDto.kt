package com.zm.footballquiz.model.dto

data class UserUpdateBody(
    val nickname: String?,
    val country: String?,
)

data class UserResponse(
    val id: Int,
    val login: String,
    val email: String,
    val nickname: String,
    val country: String,
    val role: String,
    val wallet: WalletResponse,
    val singleModeStatistics: SingleModeStatisticsResponse,
)