package com.exxlexxlee.data.mapper

import com.exxlexxlee.data.local.entities.TokenEntity
import com.exxlexxlee.domain.model.TokenData

fun TokenData.toEntity(): TokenEntity {
    return TokenEntity(
        price = this.price,
        symbol = this.symbol,
        cmcRank = this.cmcRank,
        marketCap = this.marketCap
    )
}

fun TokenEntity.toDomain(): TokenData {
    return TokenData(
        price = this.price,
        symbol = this.symbol,
        cmcRank = this.cmcRank,
        marketCap = this.marketCap
    )
}