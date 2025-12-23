package com.exxlexxlee.data.repositories

import com.exxlexxlee.data.local.dao.CountDAO
import com.exxlexxlee.data.mapper.toData
import com.exxlexxlee.data.mapper.toDomain
import com.exxlexxlee.domain.model.Count
import com.exxlexxlee.domain.repositories.CountRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class CountRepositoryImpl(
    private val countDAO: CountDAO,
) : CountRepository {

    override val activeCounts: StateFlow<List<Count>> = countDAO.activeCountsFlow()
        .map { list -> list.map { it.toDomain() } }
        .stateIn(
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    override val counts: StateFlow<List<Count>> = countDAO.countsFlow()
        .map { list -> list.map { it.toDomain() } }
        .stateIn(
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    override suspend fun save(count: Count) {
        countDAO.save(count.toData())
    }


    override suspend fun delete(id: Int) {
        countDAO.delete(id)
    }

    override suspend fun count(id: Int): Count? {
        return countDAO.count(id)?.toDomain()
    }
}