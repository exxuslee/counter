package com.exxlexxlee.domain.repositories

import com.exxlexxlee.domain.model.Player
import kotlinx.coroutines.flow.StateFlow

interface PlayersRepository {
    val players: StateFlow<List<Player>>
    val activePlayers: StateFlow<List<Player>>
    suspend fun savePlayer(player: Player): Int
    suspend fun updatePlayer(player: Player): Int
    suspend fun deletePlayer(id: Int)
    suspend fun player(id: Int): Player?
}