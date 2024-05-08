package com.zm.footballquiz.api.wallet

import com.zm.footballquiz.model.UpdateWallet
import com.zm.footballquiz.model.Wallet

interface WalletApi {

    fun updateWallet(updateWallet: UpdateWallet): Wallet?

    fun getWalletById(id: Int): Wallet?
}