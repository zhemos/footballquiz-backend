package com.zm.footballquiz.modules.wallet

import com.zm.footballquiz.model.dto.UpdateWalletBody
import com.zm.footballquiz.model.dto.WalletResponse

interface WalletController {

    suspend fun updateWallet(
        id: Int,
        updateWalletBody: UpdateWalletBody
    ): WalletResponse?
}