package com.exxlexxlee.data.local

import androidx.room.TypeConverter
import com.exxlexxlee.domain.model.Operator
import java.math.BigDecimal

class Converters {
    @TypeConverter
    fun fromBigDecimal(value: BigDecimal?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toBigDecimal(value: String?): BigDecimal? {
        return value?.let { BigDecimal(it) }
    }

    @TypeConverter
    fun fromList(list: List<Int>?): String? {
        return list?.joinToString(",")
    }

    @TypeConverter
    fun toList(value: String?): List<Int> {
        return value
            ?.split(",")
            ?.mapNotNull { it.toIntOrNull() }
            ?: emptyList()
    }

    @TypeConverter
    fun fromOperator(operator: Operator?): String? {
        return operator?.name
    }

    @TypeConverter
    fun toOperator(value: String?): Operator? {
        return value?.let { Operator.valueOf(it) }
    }
}

