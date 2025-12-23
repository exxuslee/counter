package com.exxlexxlee.domain.usecases

import com.exxlexxlee.domain.model.Player
import com.exxlexxlee.domain.repositories.PlayersRepository
import kotlinx.coroutines.flow.StateFlow

interface PlayersUseCase {

    val players: StateFlow<List<Player>>
    val activePlayers: StateFlow<List<Player>>
    suspend fun savePlayer(player: Player): Int
    suspend fun updatePlayer(player: Player)
    suspend fun deletePlayer(id: Int)
    suspend fun player(id: Int): Player?

    class Base(
        private val repository: PlayersRepository
    ) : PlayersUseCase {

        override val players: StateFlow<List<Player>> = repository.players
        override val activePlayers: StateFlow<List<Player>> = repository.activePlayers

        override suspend fun savePlayer(player: Player): Int = repository.savePlayer(player)

        override suspend fun updatePlayer(player: Player) {
            if (player.level > 0) repository.updatePlayer(player)
        }

        override suspend fun deletePlayer(id: Int) = repository.deletePlayer(id)

        override suspend fun player(id: Int) = repository.player(id)

    }
}