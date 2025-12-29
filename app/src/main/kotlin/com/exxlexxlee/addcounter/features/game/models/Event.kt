package com.exxlexxlee.addcounter.features.game.models

import com.exxlexxlee.domain.model.Count

sealed class Event {
    data class SelectPlayer(val id: Int) : Event()

    object DialogAddPlayer : Event()
    data class AddCount(val count: Count) : Event()
    data class Increment(val count: Count) : Event()
    data class Decrement(val count: Count) : Event()
}