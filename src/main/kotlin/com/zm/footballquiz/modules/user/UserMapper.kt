package com.zm.footballquiz.modules.user

import com.zm.footballquiz.model.User
import com.zm.footballquiz.model.dto.UserResponse

fun User.toDto(): UserResponse {
    return UserResponse(
        id = id,
        login = login,
        email = email,
        nickname = nickname,
        country = country,
        role = role,
    )
}