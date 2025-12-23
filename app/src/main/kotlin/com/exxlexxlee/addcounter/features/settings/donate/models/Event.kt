package com.exxlexxlee.addcounter.features.settings.donate.models

sealed class Event {
    data object AddressCopied: Event()
    data class OnAmountSelected(val amount: Int): Event()
    data class OnTickerSelected(val tickerItem: DonateItem): Event()
    data class OnChainSelected(val chainItem: DonateChainItem): Event()
    data object OnDonate: Event()
}