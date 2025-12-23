package com.exxlexxlee.addcounter.features.settings.main.models

sealed class Action {
    object NewGame : Action()
    object PopBack : Action()
    object AddPlayer : Action()

}