package com.exxlexxlee.addcounter.features.game.models

import com.exxlexxlee.domain.model.Count

sealed class Event {
    data class SelectPlayer(val id: Int) : Event()

    object DialogAddPlayer : Event()
    data class AddCount(val count: Count) : Event()
    data object AddLevel : Event()
    data object SubLevel : Event()
    data object AddBonus : Event()
    data object SubBonus : Event()
}