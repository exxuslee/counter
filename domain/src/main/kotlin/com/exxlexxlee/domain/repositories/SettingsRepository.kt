package com.exxlexxlee.domain.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SettingsRepository {

    fun isDark(): Boolean
    fun isDark(value: Boolean)

    val isSound: StateFlow<Boolean>
    fun isSound(value: Boolean)
    fun isSound(): Boolean

    fun selectedRoute(): String
    fun selectedRoute(route: String)

    val isTermsOfUseRead: Flow<Boolean>
    fun isTermsOfUseRead(): Boolean
    fun isTermsOfUseRead(ok: Boolean)

    fun priceTimestamp(): Long
    fun priceTimestamp(timestamp: Long)

}
