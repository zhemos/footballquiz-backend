package com.zm.footballquiz.db.dao

import com.zm.footballquiz.model.UpdateWallet
import com.zm.footballquiz.model.Wallet
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

object TableWallets : BaseDao("wallets"), WalletDao {
    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)
    val countOfCoins = integer("coins")
    val countOfTickets = integer("tickets")
    val countOfEnergy = integer("energy")
    val countOfRedCards = integer("redCards")
    val countOfWhistles = integer("whistles")
    val countOfFans = integer("fans")

    override fun insertWallet(): Int? {
        return insert {
            it[countOfCoins] = 0
            it[countOfTickets] = 0
            it[countOfTickets] = 0
            it[countOfEnergy] = 0
            it[countOfRedCards] = 0
            it[countOfWhistles] = 0
            it[countOfFans] = 0
            it[dateCreated] = System.currentTimeMillis()
            it[dateUpdated] = System.currentTimeMillis()
        }.getOrNull(id)
    }

    override fun getWalletById(id: Int): Wallet? {
        return select {
            TableWallets.id eq id
        }.map {
            it.mapRowToWallet()
        }.singleOrNull()
    }

    override fun getWallets(): List<Wallet> {
        return selectAll().map {
            it.mapRowToWallet()
        }
    }

    override fun updateWallet(updateWallet: UpdateWallet): Wallet? {
        update({ id eq updateWallet.id }) { statement ->
            updateWallet.countOfCoins?.let {
                statement[countOfCoins] = it
            }
            updateWallet.countOfTickets?.let {
                statement[countOfTickets] = it
            }
            updateWallet.countOfEnergy?.let {
                statement[countOfEnergy] = it
            }
            updateWallet.countOfRedCards?.let {
                statement[countOfRedCards] = it
            }
            updateWallet.countOfWhistles?.let {
                statement[countOfWhistles] = it
            }
            updateWallet.countOfFans?.let {
                statement[countOfFans] = it
            }
        }
        return getWalletById(updateWallet.id)
    }

    override fun deleteWalletById(id: Int): Int {
        return deleteWhere { TableWallets.id eq id }
    }
}

fun ResultRow.mapRowToWallet() = Wallet(
    id = this[TableWallets.id],
    countOfCoins = this[TableWallets.countOfCoins],
    countOfTickets = this[TableWallets.countOfTickets],
    countOfEnergy = this[TableWallets.countOfEnergy],
    countOfRedCards = this[TableWallets.countOfRedCards],
    countOfWhistles = this[TableWallets.countOfWhistles],
    countOfFans = this[TableWallets.countOfFans],
)

interface WalletDao {
    fun insertWallet(): Int?
    fun getWalletById(id: Int): Wallet?
    fun getWallets(): List<Wallet>
    fun updateWallet(updateWallet: UpdateWallet): Wallet?
    fun deleteWalletById(id: Int): Int
}