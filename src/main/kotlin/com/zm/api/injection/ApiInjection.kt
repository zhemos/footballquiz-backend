package com.zm.api.injection

import com.zm.api.user.UserApi
import com.zm.api.user.UserApiImpl
import org.koin.dsl.module

object ApiInjection {
    val koinBeans = module {
        single<UserApi> { UserApiImpl }
    }
}