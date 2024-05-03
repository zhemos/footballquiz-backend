package com.zm.footballquiz.api.injection

import com.zm.footballquiz.api.user.UserApi
import com.zm.footballquiz.api.user.UserApiImpl
import org.koin.dsl.module

object ApiInjection {
    val koinBeans = module {
        single<UserApi> { UserApiImpl }
    }
}