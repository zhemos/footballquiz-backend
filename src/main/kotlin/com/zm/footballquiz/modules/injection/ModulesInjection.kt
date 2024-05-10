package com.zm.footballquiz.modules.injection

import com.zm.footballquiz.modules.auth.AuthController
import com.zm.footballquiz.modules.auth.AuthControllerImpl
import com.zm.footballquiz.modules.statistics.StatisticsController
import com.zm.footballquiz.modules.statistics.StatisticsControllerImpl
import com.zm.footballquiz.modules.user.UserController
import com.zm.footballquiz.modules.user.UserControllerImpl
import com.zm.footballquiz.modules.wallet.WalletController
import com.zm.footballquiz.modules.wallet.WalletControllerImpl
import org.koin.dsl.module

object ModulesInjection {
    val koinBeans = module {
        single<AuthController> { AuthControllerImpl() }
        single<UserController> { UserControllerImpl() }
        single<StatisticsController> { StatisticsControllerImpl() }
        single<WalletController> { WalletControllerImpl() }
    }
}