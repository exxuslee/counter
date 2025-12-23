package com.exxlexxlee.domain.usecases

import android.util.Log
import com.exxlexxlee.domain.repositories.SettingsRepository
import kotlinx.coroutines.flow.Flow

interface SettingsUseCase {
    fun selectedRoute(): String
    fun selectedRoute(route: String)
    val isTermsOfUseRead: Flow<Boolean>

    fun isTermsOfUseRead(): Boolean
    fun isTermsOfUseRead(ok: Boolean)

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

    }

}



