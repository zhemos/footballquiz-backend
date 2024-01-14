package com.zm.modules.injection

import com.zm.modules.admin.AdminController
import com.zm.modules.admin.AdminControllerImpl
import org.koin.dsl.module

object ModulesInjection {
    val koinBeans = module {
        single<AdminController> { AdminControllerImpl() }
    }
}