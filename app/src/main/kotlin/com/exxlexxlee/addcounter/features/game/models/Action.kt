package com.exxlexxlee.addcounter.features.game.models

sealed class Action {
    data class ShowSelectPlayerMessage(val messageResId: Int) : Action()
    data object AddPlayer : Action()
}