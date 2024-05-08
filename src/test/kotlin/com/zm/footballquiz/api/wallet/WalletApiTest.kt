package com.zm.footballquiz.api.wallet

import com.zm.footballquiz.api.BaseApiTest
import com.zm.footballquiz.db.dao.WalletDao
import com.zm.footballquiz.instrumentation.StatisticsInstrumentation
import com.zm.footballquiz.instrumentation.WalletInstrumentation.givenFullUpdateWallet
import com.zm.footballquiz.instrumentation.WalletInstrumentation.givenFullWallet
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.TestInstance
import org.koin.dsl.module
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WalletApiTest : BaseApiTest() {

    private val walletDao: WalletDao = mockk()
    private val api: WalletApi = WalletApiImpl

    init {
        startInjection(
            module {
                single { walletDao }
            }
        )
    }

    @Before
    fun before() {
        clearMocks(walletDao)
    }

    @Test
    fun `when updating wallet and it is success, we return wallet`() {
        val id = 123
        val wallet = givenFullWallet(id)
        every { walletDao.updateWallet(any()) } returns wallet

        val updateWallet = givenFullUpdateWallet(id)
        val actual = api.updateWallet(updateWallet)
        assertEquals(wallet, actual)
    }

    @Test
    fun `when updating wallet and it is fail, we return fail`() {
        val updateWallet = givenFullUpdateWallet()
        every { walletDao.updateWallet(any()) } returns null

        val actual = api.updateWallet(updateWallet)
        assertEquals(null, actual)
    }

    @Test
    fun `when getting wallet by id and it is success, we return wallet`() {
        val id = 123
        val wallet = givenFullWallet(id)
        every { walletDao.getWalletById(any()) } returns wallet

        val actual = api.getWalletById(id)
        assertEquals(wallet, actual)
    }

    @Test
    fun `when getting wallet by id and it is fail, we return fail`() {
        val id = 123
        every { walletDao.getWalletById(any()) } returns null

        val actual = api.getWalletById(id)
        assertEquals(null, actual)
    }
}