package com.zm.footballquiz.db.injection

import com.zm.footballquiz.db.dao.TableSingleModeStatistics
import com.zm.footballquiz.db.dao.SingleModeStatisticsDao
import com.zm.footballquiz.db.dao.UserDao
import com.zm.footballquiz.db.dao.TableUsers
import org.koin.dsl.module

object DaoInjection {
    val koinBeans = module {
        single<UserDao> { TableUsers }
        single<SingleModeStatisticsDao> { TableSingleModeStatistics }
    }
}