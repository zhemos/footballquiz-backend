package com.zm.modules.injection

import com.zm.modules.auth.*
import com.zm.modules.user.*
import org.koin.dsl.module

object ModulesInjection {
    val koinBeans = module {
        single<AuthController> { AuthControllerImpl() }
        single<UserController> { UserControllerImpl() }
    }
}