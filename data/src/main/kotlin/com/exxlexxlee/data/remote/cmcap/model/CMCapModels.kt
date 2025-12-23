package com.exxlexxlee.data.remote.cmcap.model

import kotlinx.serialization.Serializable

@Serializable
data class CMCapQuote(
    val price: Double,
    val volume_24h: Double? = null,
    val percent_change_1h: Double? = null,
    val percent_change_24h: Double? = null,
    val percent_change_7d: Double? = null,
    val market_cap: Double? = null,
    val last_updated: String? = null
)

@Serializable
data class CMCapToken(
    val id: Int,
    val name: String,
    val symbol: String,
    val slug: String,
    val cmc_rank: Int,
    val num_market_pairs: Int? = null,
    val circulating_supply: Double? = null,
    val total_supply: Double? = null,
    val max_supply: Double? = null,
    val infinite_supply: Boolean? = null,
    val last_updated: String? = null,
    val date_added: String? = null,
    val tags: List<String>? = null,
    val platform: String? = null,
    val self_reported_circulating_supply: Double? = null,
    val self_reported_market_cap: Double? = null,
    val quote: Map<String, CMCapQuote>
)

@Serializable
data class CMCapResponse(
    val data: Map<String, List<CMCapToken>>,
    val status: CMCapStatus
)

@Serializable
data class CMCapStatus(
    val timestamp: String,
    val error_code: Int,
    val error_message: String? = null,
    val elapsed: Int,
    val credit_count: Int,
    val notice: String? = null
)
