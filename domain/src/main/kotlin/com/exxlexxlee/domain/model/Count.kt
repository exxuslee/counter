package com.exxlexxlee.domain.model

import java.math.BigDecimal



data class Count(
    val id: Int = 0,
    val name: String = "Count $id",
    val start: BigDecimal = BigDecimal.ZERO,
    val current: BigDecimal = BigDecimal.ZERO,
    val increment: BigDecimal = BigDecimal.ZERO,
    val operator: Operator = Operator.ADD,
    val icon: Int = 0,
    val color: Int = 0,
    val active: Boolean = true,
)