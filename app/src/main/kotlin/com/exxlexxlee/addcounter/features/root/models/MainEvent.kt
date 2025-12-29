package com.exxlexxlee.addcounter.features.root.models


sealed class MainEvent {
    data class MainRoute(val route: String) : MainEvent()
    data object OnAddCount : MainEvent()

}