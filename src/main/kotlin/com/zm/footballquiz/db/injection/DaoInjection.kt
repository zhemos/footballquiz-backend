package com.zm.footballquiz.db.injection

import com.zm.footballquiz.db.dao.*
import org.koin.dsl.module

object DaoInjection {
    val koinBeans = module {
        single<UserDao> { TableUsers }
        single<SingleModeStatisticsDao> { TableSingleModeStatistics }
        single<WalletDao> { TableWallets }
    }
}