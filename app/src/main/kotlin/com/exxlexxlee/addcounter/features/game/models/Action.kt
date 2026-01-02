package com.exxlexxlee.addcounter.features.game.models

import java.math.BigDecimal

sealed class Action {
    data object AddPlayer : Action()
    data class Sound(val new: BigDecimal) : Action()
}