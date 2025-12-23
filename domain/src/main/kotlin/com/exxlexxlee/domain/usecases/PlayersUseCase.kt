package com.exxlexxlee.domain.usecases

import com.exxlexxlee.domain.model.Count
import com.exxlexxlee.domain.repositories.CountRepository
import kotlinx.coroutines.flow.StateFlow

interface PlayersUseCase {

    val counts: StateFlow<List<Count>>
    val activeCounts: StateFlow<List<Count>>
    suspend fun save(count: Count)
    suspend fun delete(id: Int)
    suspend fun count(id: Int): Count?

    class Base(
        private val repository: CountRepository
    ) : PlayersUseCase {

        override val counts: StateFlow<List<Count>> = repository.counts
        override val activeCounts: StateFlow<List<Count>> = repository.activeCounts

        override suspend fun save(count: Count) = repository.save(count)

        override suspend fun delete(id: Int) = repository.delete(id)

        override suspend fun count(id: Int) = repository.count(id)

    }
}