package com.zm.db.injection

import com.zm.db.dao.UserDao
import com.zm.db.dao.Users
import org.koin.dsl.module

object DaoInjection {
    val koinBeans = module {
        single<UserDao> { Users }
    }
}