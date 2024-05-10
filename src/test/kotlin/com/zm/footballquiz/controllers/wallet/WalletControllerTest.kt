package com.zm.footballquiz.controllers.wallet

import com.zm.footballquiz.api.wallet.WalletApi
import com.zm.footballquiz.controllers.BaseControllerTest
import com.zm.footballquiz.instrumentation.WalletInstrumentation.givenDefaultWallet
import com.zm.footballquiz.instrumentation.WalletInstrumentation.givenDefaultWalletResponse
import com.zm.footballquiz.instrumentation.WalletInstrumentation.givenUpdateWalletBodyOfTypeUnknownFull
import com.zm.footballquiz.instrumentation.WalletInstrumentation.givenUpdateWalletBodyOfTypeUnknownParticular
import com.zm.footballquiz.instrumentation.WalletInstrumentation.givenUpdateWalletBodyOfTypeUpdateFull
import com.zm.footballquiz.model.dto.SingleModeStatisticsResponse
import com.zm.footballquiz.modules.wallet.WalletController
import com.zm.footballquiz.modules.wallet.WalletControllerImpl
import com.zm.footballquiz.statuspages.ApplicationException
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
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

    @Test
    fun `when update wallet and type is unknown full, return exception`() {
        val updateWallet = givenUpdateWalletBodyOfTypeUnknownFull()
        val wallet = givenDefaultWallet()
        coEvery { walletApi.getWalletById(any()) } returns wallet
        Assert.assertThrows(ApplicationException.Generic::class.java) {
            runBlocking { controller.updateWallet(1, updateWallet) }
        }
    }

    @Test
    fun `when update wallet and type is unknown particular, return exception`() {
        val updateWallet = givenUpdateWalletBodyOfTypeUnknownParticular()
        val wallet = givenDefaultWallet()
        coEvery { walletApi.getWalletById(any()) } returns wallet
        Assert.assertThrows(ApplicationException.Generic::class.java) {
            runBlocking { controller.updateWallet(1, updateWallet) }
        }
    }

    @Test
    fun `when update wallet and type is update full, return new wallet`() {
        val value = 10
        val updateWallet = givenUpdateWalletBodyOfTypeUpdateFull(value = value)
        val wallet = givenDefaultWallet()
        coEvery { walletApi.getWalletById(any()) } returns wallet
        coEvery { walletApi.updateWallet(any()) } returns wallet

        val expected = givenDefaultWalletResponse()
        runBlocking {
            val actual = controller.updateWallet(1, updateWallet)
            assertEquals(expected, actual)
        }
    }
}