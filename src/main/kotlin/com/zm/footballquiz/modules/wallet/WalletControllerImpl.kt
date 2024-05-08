package com.zm.footballquiz.modules.wallet

import com.zm.footballquiz.api.wallet.WalletApi
import com.zm.footballquiz.model.dto.UpdateWalletBody
import com.zm.footballquiz.model.dto.WalletResponse
import com.zm.footballquiz.modules.BaseController
import org.koin.core.component.inject

class WalletControllerImpl : BaseController(), WalletController {

    private val walletApi by inject<WalletApi>()

    override suspend fun updateWallet(
        id: Int,
        updateWalletBody: UpdateWalletBody
    ): WalletResponse? {
        //walletApi.updateWallet()
        TODO("Not yet implemented")
    }
}