package com.zm.footballquiz.api.injection

import com.zm.footballquiz.api.statistics.StatisticsApi
import com.zm.footballquiz.api.statistics.StatisticsApiImpl
import com.zm.footballquiz.api.user.UserApi
import com.zm.footballquiz.api.user.UserApiImpl
import com.zm.footballquiz.api.wallet.WalletApi
import com.zm.footballquiz.api.wallet.WalletApiImpl
import org.koin.dsl.module

object ApiInjection {
    val koinBeans = module {
        single<UserApi> { UserApiImpl }
        single<StatisticsApi> { StatisticsApiImpl }
        single<WalletApi> { WalletApiImpl }
    }
}