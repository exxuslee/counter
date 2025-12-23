package com.exxlexxlee.addcounter.navigation

fun Routes.isParentSelected(currentRoute: String?): Boolean {
    return when (this) {
        is Routes.SettingsRoute -> currentRoute?.startsWith("settings/") == true
        else -> false
    }
}