package com.zm.footballquiz.db.injection

import com.zm.footballquiz.db.dao.UserDao
import com.zm.footballquiz.db.dao.Users
import org.koin.dsl.module

object DaoInjection {
    val koinBeans = module {
        single<UserDao> { Users }
    }
}