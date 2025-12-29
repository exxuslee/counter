package com.exxlexxlee.addcounter.features.game.models

sealed class Action {
    data object AddPlayer : Action()
}