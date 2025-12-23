package com.exxlexxlee.domain.repositories

import com.exxlexxlee.domain.model.Count
import kotlinx.coroutines.flow.StateFlow
import java.math.BigDecimal

interface CountRepository {
    val counts: StateFlow<List<Count>>
    val activeCounts: StateFlow<List<Count>>
    suspend fun save(count: Count)
    suspend fun delete(id: Int)
    suspend fun count(id: Int): Count?
}