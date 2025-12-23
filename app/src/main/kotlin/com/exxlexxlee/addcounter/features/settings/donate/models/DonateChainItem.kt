package com.exxlexxlee.addcounter.features.settings.donate.models

import com.exxlexxlee.addcounter.R


sealed class DonateChainItem(
    val chain: String,
    val address: String,
    val icon: Int,
) {
    data object Bitcoin : DonateChainItem(
        chain = "Bitcoin",
        address = "36b5Z19fLrbgEcV1dwhwiFjix86bGweXKC",
        icon = R.drawable.outline_currency_bitcoin_24,
    )

    data object Ethereum : DonateChainItem(
        chain = "Ethereum",
        address = "0x6F1C4B2bd0489e32AF741C405CcA696E8a95ce9C",
        icon = R.drawable.icons8_ethereum_80,
    )

    data object BSC : DonateChainItem(
        chain = "Binance-smart-chain",
        address = "0x6F1C4B2bd0489e32AF741C405CcA696E8a95ce9C",
        icon = R.drawable.icon_bnb,
    )

    data object Solana : DonateChainItem(
        chain = "Solana",
        address = "2zMufqDhhiMbcQRVLiAVrBv9SWdHvxrHgAsdQfMbUaJS",
        icon = R.drawable.icon_solana,
    )

    data object Tron : DonateChainItem(
        chain = "Tron",
        address = "TKQMJN2aFAyPwaFCdg3AxhRT9xqsRuTvb3",
        icon = R.drawable.icon_tron,
    )

    companion object {
        fun chains() = listOf(Bitcoin, Ethereum, BSC, Solana, Tron)
    }

}