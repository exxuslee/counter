package com.exxlexxlee.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.exxlexxlee.domain.model.Operator
import java.math.BigDecimal


@Entity(tableName = "Counts")
data class CountEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val start: BigDecimal,
    val current: BigDecimal,
    val increment: BigDecimal,
    val operator: Operator,
    val icon: Int,
    val color: Int,
    val photos: List<Int>,
    val active: Boolean,
)