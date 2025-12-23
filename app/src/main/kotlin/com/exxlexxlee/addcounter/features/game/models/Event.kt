package com.exxlexxlee.addcounter.features.game.models

import com.exxlexxlee.domain.model.Count

sealed class Event {
    data class SelectPlayer(val id: Int) : Event()
    data class SwitchSex(val count: Count) : Event()

    object DialogAddPlayer : Event()
    data class AddPlayer(val name: String, val icon: Int) : Event()
    data object AddLevel : Event()
    data object SubLevel : Event()
    data object AddBonus : Event()
    data object SubBonus : Event()
}