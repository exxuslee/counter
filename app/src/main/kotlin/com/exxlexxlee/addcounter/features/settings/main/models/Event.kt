package com.exxlexxlee.addcounter.features.settings.main.models

import com.exxlexxlee.domain.model.Count

sealed class Event {
    data class IsDark(val newValue: Boolean) : Event()
    data class IsSound(val newValue: Boolean) : Event()
    object ConfirmNewGame : Event()
    object DialogNewGame : Event()
    object DialogAddPlayer : Event()
    data class AddCount(val count: Count) : Event()
    data class ActivatePlayer(val count: Count) : Event()
    data class Reveal(val id: Int) : Event()
    data class DeletePlayer(val id: Int) : Event()


}