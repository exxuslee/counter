package com.exxlexxlee.addcounter.features.game.models

sealed class Event {
    data class SelectPlayer(val id: Int) : Event()

    object DialogAddPlayer : Event()
    data class AddPlayer(val name: String, val icon: Int, val colorId: Int) : Event()
    data object AddLevel : Event()
    data object SubLevel : Event()
    data object AddBonus : Event()
    data object SubBonus : Event()
}