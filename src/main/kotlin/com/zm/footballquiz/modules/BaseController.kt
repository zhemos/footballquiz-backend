package com.zm.footballquiz.modules

import com.zm.footballquiz.db.DatabaseProviderContract
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BaseController : KoinComponent {

    private val dbProvider by inject<DatabaseProviderContract>()

    suspend fun <T> dbQuery(block: () -> T): T {
        return dbProvider.dbQuery(block)
    }
}