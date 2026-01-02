package com.exxlexxlee.domain.usecases

import android.util.Log
import com.exxlexxlee.domain.repositories.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SettingsUseCase {
    fun selectedRoute(): String
    fun selectedRoute(route: String)
    val isTermsOfUseRead: Flow<Boolean>

    fun isTermsOfUseRead(): Boolean
    fun isTermsOfUseRead(ok: Boolean)

    val isSound: StateFlow<Boolean>
    fun setSound(value: Boolean)
    fun isSound(): Boolean

    class Base(
        private val settingsRepository: SettingsRepository,
    ) : SettingsUseCase {
        override fun selectedRoute() = settingsRepository.selectedRoute()

        override fun selectedRoute(route: String) = settingsRepository.selectedRoute(route)

        override val isTermsOfUseRead = settingsRepository.isTermsOfUseRead

        override fun isTermsOfUseRead(): Boolean {
            val isTermsOfUseRead = settingsRepository.isTermsOfUseRead()
            Log.d("SettingsUseCase", "isTermsOfUseRead $isTermsOfUseRead")
            return isTermsOfUseRead
        }

        override fun isTermsOfUseRead(ok: Boolean) {
            settingsRepository.isTermsOfUseRead(ok)
        }

        override val isSound: StateFlow<Boolean> = settingsRepository.isSound

        override fun setSound(value: Boolean) {
            settingsRepository.isSound(value)
        }

        override fun isSound() = settingsRepository.isSound()

    }

}



