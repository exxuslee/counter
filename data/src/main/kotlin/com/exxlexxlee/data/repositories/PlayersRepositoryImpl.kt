package com.exxlexxlee.data.repositories

import com.exxlexxlee.data.local.dao.PlayerDAO
import com.exxlexxlee.data.mapper.toData
import com.exxlexxlee.data.mapper.toDomain
import com.exxlexxlee.domain.model.Player
import com.exxlexxlee.domain.repositories.PlayersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class PlayersRepositoryImpl(
    private val playerDAO: PlayerDAO,
) : PlayersRepository {

    override val activePlayers: StateFlow<List<Player>> = playerDAO.activePlayersFlow()
        .map { list -> list.map { it.toDomain() } }
        .stateIn(
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    override val players: StateFlow<List<Player>> = playerDAO.playersFlow()
        .map { list -> list.map { it.toDomain() } }
        .stateIn(
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    override suspend fun savePlayer(player: Player): Int {
        playerDAO.savePlayer(player.toData())
        return playerDAO.lastID() ?: -1
    }

    override suspend fun updatePlayer(player: Player): Int {
        playerDAO.updatePlayer(
            player.id,
            player.level,
            player.bonus,
            player.reverseSex,
            player.playing
        )
        return 0
    }

    override suspend fun deletePlayer(id: Int) {
        playerDAO.deletePlayer(id)
    }

    override suspend fun player(id: Int): Player? {
        return playerDAO.player(id)?.toDomain()
    }
}