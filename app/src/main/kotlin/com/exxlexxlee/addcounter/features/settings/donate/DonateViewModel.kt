package com.exxlexxlee.addcounter.features.settings.donate

import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import com.exxlexxlee.domain.model.TokenData
import com.exxlexxlee.domain.usecases.PriceUseCase
import com.exxlexxlee.addcounter.features.settings.donate.models.Action
import com.exxlexxlee.addcounter.features.settings.donate.models.DonateChainItem
import com.exxlexxlee.addcounter.features.settings.donate.models.DonateItem
import com.exxlexxlee.addcounter.features.settings.donate.models.Event
import com.exxlexxlee.addcounter.features.settings.donate.models.ViewState
import com.exxlexxlee.addcounter.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode

class DonateViewModel(
    val priceUseCase: PriceUseCase,
) : BaseViewModel<ViewState, Action, Event>(
    initialState = ViewState(
        donates = DonateChainItem.chains(),
        tickers = DonateItem.trxList(),
        selectedTicker = DonateItem.Usdt,
        selectedChain = DonateChainItem.Tron,
    )
) {
    private var tokens: List<TokenData> = emptyList()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            tokens = priceUseCase.refresh()
        }
    }

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {

            is Event.OnAmountSelected -> amountSelected(viewEvent.amount)

            is Event.OnChainSelected -> chainSelected(viewEvent.chainItem)

            is Event.OnTickerSelected -> tickerSelected(viewEvent.tickerItem)

            Event.AddressCopied -> viewState = viewState.copy(isAddressCopied = true)

            Event.OnDonate -> {
                viewState = viewState.copy(isAddressCopied = true)
                openCryptoDonate()
            }
        }
    }

    private fun tickerSelected(tickerItem: DonateItem) {
        if (tickerItem is DonateItem.Usdt || viewState.selectedTicker is DonateItem.Usdc) {
            viewState = viewState.copy(
                selectedTicker = tickerItem,
                outAmount = viewState.selectedAmount.toString(),
            )
            return
        }
        val price = tokens.firstOrNull { it.symbol == tickerItem.label }?.price ?: return
        val amountInToken = viewState.selectedAmount.toDouble() / price.toDouble()
        val rounded = BigDecimal(amountInToken, MathContext(3, RoundingMode.HALF_UP))
        viewState = viewState.copy(
            selectedTicker = tickerItem,
            outAmount = rounded.toPlainString(),
        )
        viewState = viewState.copy(selectedTicker = tickerItem)
    }

    private fun amountSelected(amount: Int) {
        if (viewState.selectedTicker is DonateItem.Usdt || viewState.selectedTicker is DonateItem.Usdc) {
            viewState = viewState.copy(
                selectedAmount = amount,
                outAmount = amount.toString(),
            )
            return
        }
        val ticker = viewState.selectedTicker.label
        val price = tokens.firstOrNull { it.symbol == ticker }?.price ?: return
        val amountInToken = amount.toDouble() / price.toDouble()
        val rounded = BigDecimal(amountInToken, MathContext(3, RoundingMode.HALF_UP))
        viewState = viewState.copy(
            selectedAmount = amount,
            outAmount = rounded.toPlainString(),
        )
    }

    private fun chainSelected(chain: DonateChainItem) {
        when (chain) {
            DonateChainItem.BSC -> {
                val selectedTicker = if (DonateItem.bnbList().contains(viewState.selectedTicker)) {
                    viewState.selectedTicker
                } else DonateItem.Usdt
                viewState = viewState.copy(
                    tickers = DonateItem.bnbList(),
                    selectedChain = chain,
                    selectedTicker = selectedTicker,
                    outAmount = viewState.selectedAmount.toString(),
                )
            }

            DonateChainItem.Bitcoin -> {
                val price =
                    tokens.firstOrNull { it.symbol == "BTC" }?.price ?: BigDecimal("80000")
                val amountInToken = viewState.selectedAmount.toDouble() / price.toDouble()
                val rounded = BigDecimal(amountInToken, MathContext(3, RoundingMode.HALF_UP))
                viewState = viewState.copy(
                    tickers = DonateItem.btcList(),
                    selectedChain = chain,
                    selectedTicker = DonateItem.Bitcoin,
                    outAmount = rounded.toPlainString(),
                )
            }

            DonateChainItem.Ethereum -> {
                val selectedTicker = if (DonateItem.ethList().contains(viewState.selectedTicker)) {
                    viewState.selectedTicker
                } else DonateItem.Usdt
                viewState = viewState.copy(
                    tickers = DonateItem.ethList(),
                    selectedChain = chain,
                    selectedTicker = selectedTicker,
                    outAmount = viewState.selectedAmount.toString(),
                )
            }

            DonateChainItem.Solana -> {
                val selectedTicker = if (DonateItem.solList().contains(viewState.selectedTicker)) {
                    viewState.selectedTicker
                } else DonateItem.Usdt
                viewState = viewState.copy(
                    tickers = DonateItem.solList(),
                    selectedChain = chain,
                    selectedTicker = selectedTicker,
                    outAmount = viewState.selectedAmount.toString(),
                )
            }

            DonateChainItem.Tron -> {
                val selectedTicker = if (DonateItem.trxList().contains(viewState.selectedTicker)) {
                    viewState.selectedTicker
                } else DonateItem.Usdt
                viewState = viewState.copy(
                    tickers = DonateItem.trxList(),
                    selectedChain = chain,
                    selectedTicker = selectedTicker,
                    outAmount = viewState.selectedAmount.toString(),
                )
            }
        }
    }

    fun openCryptoDonate() {
        val chain: DonateChainItem = viewState.selectedChain
        val ticker: DonateItem = viewState.selectedTicker
        val amount: String = viewState.outAmount

        val uri = when (chain) {

            DonateChainItem.Bitcoin -> {
                "bitcoin:${chain.address}?amount=$amount".toUri()
            }

            DonateChainItem.Ethereum -> when (ticker) {
                DonateItem.Ethereum -> {
                    val weiAmount: BigInteger =
                        amount.toBigDecimal()
                            .multiply(BigDecimal("1000000000000000000")) // 1e18
                            .setScale(0, RoundingMode.DOWN)
                            .toBigInteger()
                    "ethereum:${chain.address}@1?value=$weiAmount".toUri()
                }

                DonateItem.Usdt, DonateItem.Usdc -> {
                    val contract = when (ticker) {
                        DonateItem.Usdt -> "0xdAC17F958D2ee523a2206206994597C13D831ec7"
                        DonateItem.Usdc -> "0xA0b86991c6218b36c1d19D4a2e9Eb0cE3606eB48"
                        else -> error("Unsupported token")
                    }
                    val tokenAmount: BigInteger =
                        amount.toBigDecimal()
                            .multiply(BigDecimal("1000000000000000000"))
                            .setScale(0, RoundingMode.DOWN)
                            .toBigInteger()
                    val to = chain.address
                    "ethereum:$contract@1/transfer?address=$to&uint256=$tokenAmount".toUri()
                }

                else -> null
            }

            DonateChainItem.BSC -> when (ticker) {
                DonateItem.Bnb -> {
                    val weiAmount: BigInteger = amount.toBigDecimal()
                        .multiply(BigDecimal("1000000000000000000"))
                        .setScale(0, RoundingMode.DOWN)
                        .toBigInteger()
                    "ethereum:${chain.address}@56?value=$weiAmount".toUri()
                }

                DonateItem.Usdt, DonateItem.Usdc -> {
                    val contract = when (ticker) {
                        DonateItem.Usdt -> "0x55d398326f99059fF775485246999027B3197955"
                        DonateItem.Usdc -> "0x8ac76a51cc950d9822d68b83fe1ad97b32cd580d"
                        else -> error("Unsupported token")
                    }
                    val tokenAmount: BigInteger = amount.toBigDecimal()
                        .multiply(BigDecimal("1000000000000000000"))
                        .setScale(0, RoundingMode.DOWN)
                        .toBigInteger()

                    val to = chain.address
                    "ethereum:$contract@56/transfer?address=$to&uint256=$tokenAmount".toUri()
                }

                else -> null
            }

            DonateChainItem.Solana -> when (ticker) {
                DonateItem.Solana -> {
                    "solana:${chain.address}?amount=$amount".toUri()
                }

                DonateItem.Usdt, DonateItem.Usdc -> {
                    val mint = when (ticker) {
                        DonateItem.Usdt -> "Es9vMFrzaCERmJfrF4H2FYD4KCoNkY11McCe8BenwNYB"
                        DonateItem.Usdc -> "EPjFWdd5AufqSSqeM2qN1xzybapC8G4wEGGkZwyTDt1v"
                        else -> error("Unsupported token")
                    }

                    val tokenAmount = amount.toBigDecimal()
                        .multiply(BigDecimal("1000000"))
                        .setScale(0, RoundingMode.DOWN)
                        .toBigInteger()

                    "solana:${chain.address}?amount=$tokenAmount&spl-token=$mint".toUri()
                }

                else -> null
            }

            DonateChainItem.Tron -> {
                "tron:${chain.address}?amount=$amount".toUri()
            }
        }

        viewModelScope.launch {
            delay(500)
            uri?.let { viewAction = Action.Donate(it) }
        }
    }

}