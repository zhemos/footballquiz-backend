package com.zm.footballquiz.instrumentation

import com.zm.footballquiz.instrumentation.StatisticsInstrumentation.givenSingleModeStatistics
import com.zm.footballquiz.model.User

object UserInstrumentation {

    fun givenUser(id: Int = 123): User {
        val singleModeStatistics = givenSingleModeStatistics()
        val wallet = WalletInstrumentation.givenDefaultWallet()
        return User(
            id = id,
            login = "login",
            email = "user@gmail.com",
            password = "root16",
            role = "user",
            nickname = "",
            country = "by",
            wallet = wallet,
            singleModeStatistics = singleModeStatistics,
        )
    }
}