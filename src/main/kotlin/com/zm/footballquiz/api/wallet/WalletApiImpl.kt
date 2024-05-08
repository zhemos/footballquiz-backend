package com.zm.footballquiz.api.wallet

import com.zm.footballquiz.db.dao.WalletDao
import com.zm.footballquiz.model.UpdateWallet
import com.zm.footballquiz.model.Wallet
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object WalletApiImpl : WalletApi, KoinComponent {

    private val walletDao by inject<WalletDao>()

    override fun updateWallet(updateWallet: UpdateWallet): Wallet? {
        return walletDao.updateWallet(updateWallet)
    }

    override fun getWalletById(id: Int): Wallet? {
        return walletDao.getWalletById(id)
    }
}