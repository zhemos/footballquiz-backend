package com.zm.footballquiz.modules.user

import com.zm.footballquiz.model.User
import com.zm.footballquiz.model.dto.UserResponse
import com.zm.footballquiz.modules.statistics.toDto
import com.zm.footballquiz.modules.wallet.toDto

fun User.toDto(): UserResponse {
    return UserResponse(
        id = id,
        login = login,
        email = email,
        nickname = nickname,
        country = country,
        role = role,
        wallet = wallet.toDto(),
        singleModeStatistics = singleModeStatistics.toDto()
    )
}