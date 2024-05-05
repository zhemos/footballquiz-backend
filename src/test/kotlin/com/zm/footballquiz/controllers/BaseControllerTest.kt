package com.zm.footballquiz.controllers

import com.zm.footballquiz.db.DatabaseProviderContract
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module

abstract class BaseControllerTest {

    private val databaseProvider: DatabaseProviderContract = mockk()

    init {
        stopKoin()
    }

    open fun before() {
        clearMocks(databaseProvider)
        coEvery { databaseProvider.dbQuery(any<() -> Any>()) } coAnswers {
            firstArg<() -> Any>().invoke()
        }
    }

    fun startInjection(module: Module) {
        startKoin {
            modules(
                module,
                org.koin.dsl.module {
                    single { databaseProvider }
                }
            )
        }
    }
}