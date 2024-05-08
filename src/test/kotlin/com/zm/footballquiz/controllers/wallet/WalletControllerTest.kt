package com.zm.footballquiz.controllers.wallet

import com.zm.footballquiz.api.wallet.WalletApi
import com.zm.footballquiz.controllers.BaseControllerTest
import com.zm.footballquiz.model.dto.UpdateStatisticsType
import com.zm.footballquiz.modules.wallet.WalletController
import com.zm.footballquiz.modules.wallet.WalletControllerImpl
import io.mockk.clearMocks
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.TestInstance
import org.koin.dsl.module
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WalletControllerTest : BaseControllerTest() {

    private val walletApi: WalletApi = mockk()
    private val controller: WalletController by lazy { WalletControllerImpl() }

    init {
        startInjection(
            module {
                single { walletApi }
            }
        )
    }

    @Before
    override fun before() {
        super.before()
        clearMocks(walletApi)
    }
}