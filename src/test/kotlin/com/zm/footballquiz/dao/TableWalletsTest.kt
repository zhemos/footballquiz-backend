package com.zm.footballquiz.dao

import com.zm.footballquiz.db.dao.TableWallets
import com.zm.footballquiz.instrumentation.WalletInstrumentation.givenDefaultWallet
import com.zm.footballquiz.instrumentation.WalletInstrumentation.givenEmptyUpdateWallet
import com.zm.footballquiz.instrumentation.WalletInstrumentation.givenFullUpdateWallet
import com.zm.footballquiz.instrumentation.WalletInstrumentation.givenFullWallet
import org.junit.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TableWalletsTest : BaseDaoTest() {

    @Test
    fun `insert and get by id wallet`() = withTables(TableWallets) {
        val wallet = givenDefaultWallet(id = 1)
        val id = TableWallets.insertWallet() ?: throw IllegalStateException("id cannot be null")

        val actual = TableWallets.getWalletById(id)
        assertEquals(wallet, actual)
    }

    @Test
    fun `get all wallets`() = withTables(TableWallets) {
        val id1 = TableWallets.insertWallet() ?: throw IllegalStateException("id cannot be null")
        val id2 = TableWallets.insertWallet() ?: throw IllegalStateException("id cannot be null")
        val wallets = listOf(
            givenDefaultWallet(id = id1),
            givenDefaultWallet(id = id2)
        )

        val actual = TableWallets.getWallets()
        assertEquals(actual.size, wallets.size)
        assertEquals(actual, wallets)
    }

    @Test
    fun `full update wallets`() = withTables(TableWallets) {
        val id = TableWallets.insertWallet() ?: throw IllegalStateException("id cannot be null")
        val updateWallet = givenFullUpdateWallet(id = id)
        val wallet = givenFullWallet(id = id)

        val actual = TableWallets.updateWallet(updateWallet)
        assertEquals(wallet, actual)
    }

    @Test
    fun `empty update wallets`() = withTables(TableWallets) {
        val id = TableWallets.insertWallet() ?: throw IllegalStateException("id cannot be null")
        val updateWallet = givenEmptyUpdateWallet(id = id).copy(countOfCoins = 1)
        val wallet = givenDefaultWallet(id = id).copy(countOfCoins = 1)

        val actual = TableWallets.updateWallet(updateWallet)
        assertEquals(wallet, actual)
    }

    @Test
    fun `delete wallet`() = withTables(TableWallets) {
        val id = TableWallets.insertWallet() ?: throw IllegalStateException("id cannot be null")
        val deleteId = TableWallets.deleteWalletById(id)
        assertEquals(id, deleteId)
    }
}