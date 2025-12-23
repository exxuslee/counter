package com.exxlexxlee.addcounter.features.game.models

import com.exxlexxlee.domain.model.Count
import com.exxlexxlee.domain.model.UiState

data class GameViewState(
    val isDark: Boolean = false,
    val activeCounts: List<Count> = emptyList(),
    val allPlayers: Int = 0,
    val selectedPlayerId: Int? = null,
    val state: UiState = UiState.Loading,
)