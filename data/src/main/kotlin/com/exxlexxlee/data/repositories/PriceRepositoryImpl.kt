package com.exxlexxlee.data.repositories

import android.util.Log
import com.exxlexxlee.data.local.dao.TokensDAO
import com.exxlexxlee.data.mapper.toDomain
import com.exxlexxlee.data.mapper.toEntity
import com.exxlexxlee.data.remote.cmcap.CMCapService
import com.exxlexxlee.domain.model.TokenData
import com.exxlexxlee.domain.repositories.PriceRepository
import com.exxlexxlee.domain.repositories.SettingsRepository
import java.math.BigDecimal

class PriceRepositoryImpl(
    private val tokensDAO: TokensDAO,
    private val cmCapService: CMCapService,
    private val settingsRepository: SettingsRepository,
) : PriceRepository {

    override suspend fun price(coin: String): BigDecimal? {
        TODO("Not yet implemented")
    }

    override suspend fun refresh(): List<TokenData> {
        val lastUpdate = settingsRepository.priceTimestamp()
        val currentTime = System.currentTimeMillis()

        if (currentTime - lastUpdate < 2 * 60 * 60 * 1000) {
            val cachedPrices = tokensDAO.tokens()
            if (cachedPrices.isNotEmpty()) return cachedPrices.map { it.toDomain() }
        }

        return try {
            val tokens = cmCapService.topTokens()
            if (tokens.isEmpty()) return listOf()
            tokensDAO.insertAll(tokens.map { it.toEntity() })
            if (tokens.isNotEmpty()) settingsRepository.priceTimestamp(System.currentTimeMillis())
            tokens
        } catch (e: Exception) {
            Log.e("PriceRepositoryImpl", "Error fetching prices: ${e.message}")
            val cachedPrices = tokensDAO.tokens()
            cachedPrices.map { it.toDomain() }
        }

    }

}
