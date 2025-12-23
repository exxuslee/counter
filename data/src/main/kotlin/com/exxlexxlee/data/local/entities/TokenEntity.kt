package com.exxlexxlee.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal


@Entity(tableName = "Tokens")
data class TokenEntity(
    @PrimaryKey val symbol: String,
    val cmcRank: Int,
    val marketCap: Long,
    val price: BigDecimal,
)