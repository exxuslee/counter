package com.exxlexxlee.domain.model

import java.math.BigDecimal

data class TokenData(
    val symbol: String,
    val cmcRank: Int,
    val marketCap: Long,
    val price: BigDecimal,
)