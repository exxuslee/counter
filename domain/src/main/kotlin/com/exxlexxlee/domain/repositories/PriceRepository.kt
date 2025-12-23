package com.exxlexxlee.domain.repositories

import com.exxlexxlee.domain.model.TokenData
import java.math.BigDecimal

interface PriceRepository {
    suspend fun price(coin: String): BigDecimal?
    suspend fun refresh(): List<TokenData>
}