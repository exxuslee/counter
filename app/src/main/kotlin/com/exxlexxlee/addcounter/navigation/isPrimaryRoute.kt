package com.exxlexxlee.addcounter.navigation

fun Routes.isPrimaryRoute(): Boolean {
    return when (this) {
        is Routes.GameRoute,
            -> true

        else -> false
    }
}