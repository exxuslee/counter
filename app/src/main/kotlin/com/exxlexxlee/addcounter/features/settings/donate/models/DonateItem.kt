package com.exxlexxlee.addcounter.features.settings.donate.models

import com.exxlexxlee.addcounter.R


sealed class DonateItem(
    val label: String,
    val icon: Int,
) {
    data object Bitcoin : DonateItem(
        label = "BTC", icon = R.drawable.icons8_bitcoin_80,
    )

    data object Ethereum : DonateItem(
        label = "ETH", icon = R.drawable.icons8_ethereum_80
    )

    data object Bnb : DonateItem(
        label = "BNB", icon = R.drawable.icon_bnb
    )

    data object Solana : DonateItem(
        label = "SOL", icon = R.drawable.icon_solana
    )

    data object Tron : DonateItem(
        label = "TRX", icon = R.drawable.icon_tron
    )

    data object Usdt : DonateItem(
        label = "USDT", icon = R.drawable.icons8_tether_80
    )

    data object Usdc : DonateItem(
        label = "USDC", icon = R.drawable.icons8_usdc_80
    )

    companion object {
        fun bnbList() = listOf(Bnb, Usdt, Usdc)
        fun ethList() = listOf(Ethereum, Usdt, Usdc)
        fun trxList() = listOf(Tron, Usdt, Usdc)
        fun solList() = listOf(Solana, Usdt, Usdc)
        fun btcList() = listOf(Bitcoin)
    }

}