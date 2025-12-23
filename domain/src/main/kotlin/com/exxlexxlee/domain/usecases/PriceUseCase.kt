package com.exxlexxlee.domain.usecases

import com.exxlexxlee.domain.model.TokenData
import com.exxlexxlee.domain.repositories.PriceRepository
import java.math.BigDecimal

interface PriceUseCase {
    suspend fun refresh(): List<TokenData>
    suspend fun price(coin: String): BigDecimal?

    class Base(
        private val priceRepository: PriceRepository,
    ) : PriceUseCase {

        override suspend fun refresh(): List<TokenData> {
            return priceRepository.refresh()
        }

        override suspend fun price(coin: String): BigDecimal? {
            return priceRepository.price(coin)
        }

    }
}