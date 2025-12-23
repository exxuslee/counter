package com.exxlexxlee.data.repositories

import android.content.SharedPreferences
import androidx.core.content.edit
import com.exxlexxlee.domain.repositories.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsRepositoryImpl(
    private val prefs: SharedPreferences,
) : SettingsRepository {
    private companion object {
        const val KEY_IS_DARK = "isDark"
        const val KEY_SELECTED_ROUTE = "selectedRoute"
        const val KEY_TERMS_OF_USE_READ = "isTermsOfUseRead"
        const val DEFAULT_ROUTE = "game"
        const val PRICE_TIMESTAMP = "priceTimestamp"
    }

    override fun isDark(): Boolean = prefs.getBoolean(KEY_IS_DARK, false)
    override fun isDark(value: Boolean) = prefs.edit { putBoolean(KEY_IS_DARK, value) }


    override fun selectedRoute(): String =
        prefs.getString(KEY_SELECTED_ROUTE, DEFAULT_ROUTE) ?: DEFAULT_ROUTE

    override fun selectedRoute(route: String) = prefs.edit { putString(KEY_SELECTED_ROUTE, route) }


    private val _isTermsOfUseRead = MutableStateFlow(isTermsOfUseRead())
    override val isTermsOfUseRead: StateFlow<Boolean> = _isTermsOfUseRead.asStateFlow()
    override fun isTermsOfUseRead(): Boolean =
        prefs.getBoolean(KEY_TERMS_OF_USE_READ, false)

    override fun isTermsOfUseRead(ok: Boolean) {
        if (_isTermsOfUseRead.value != ok) {
            prefs.edit { putBoolean(KEY_TERMS_OF_USE_READ, ok) }
            _isTermsOfUseRead.value = ok
        }
    }

    override fun priceTimestamp(): Long {
        return prefs.getLong(PRICE_TIMESTAMP, System.currentTimeMillis())
    }

    override fun priceTimestamp(timestamp: Long) {
        prefs.edit { putLong(PRICE_TIMESTAMP, timestamp) }
    }


}