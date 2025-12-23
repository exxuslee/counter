package com.exxlexxlee.domain.usecases

import com.exxlexxlee.domain.repositories.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface ThemeController {
    val isDark: StateFlow<Boolean>

    fun setDark(value: Boolean)

    class Base(
        private val settingsRepository: SettingsRepository,
    ) : ThemeController {
        private val _isDark = MutableStateFlow(settingsRepository.isDark())
        override val isDark: StateFlow<Boolean> = _isDark.asStateFlow()

        override fun setDark(value: Boolean) {
            settingsRepository.isDark(value)
            _isDark.value = value
        }
    }
}