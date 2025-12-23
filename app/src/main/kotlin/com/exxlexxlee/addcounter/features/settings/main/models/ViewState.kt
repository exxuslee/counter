package com.exxlexxlee.addcounter.features.settings.main.models

import com.exxlexxlee.domain.model.Player

data class ViewState(
    val isDark: Boolean = false,
    val isTermsOfUseRead: Boolean = false,
    val players: List<Player> = emptyList(),
    val revealedId: Int = -1,
)