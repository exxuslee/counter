package com.exxlexxlee.data.remote.cmcap.api

import com.exxlexxlee.data.BuildConfig
import com.exxlexxlee.data.remote.cmcap.CMCapService
import com.exxlexxlee.data.remote.safeCall
import com.exxlexxlee.domain.model.TokenData
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class CMCapServiceImpl(
    private val httpClient: HttpClient,
) : CMCapService {

    companion object {
        private const val BASE_URL =
            "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest"
        private const val CHUNK_SIZE = 30

    }
    val apiList = BuildConfig.CMC_API_KEY.split(",").map { it.trim() }

    override suspend fun topTokens(): List<TokenData> {
        val cmcRanks = mutableListOf<TokenData>()

        val response = safeCall {
            httpClient.get(BASE_URL) {
                headers {
                    append("X-CMC_PRO_API_KEY", apiList.random())
                    append("Accept", "application/json")
                }
                parameter("limit", CHUNK_SIZE)
            }
        }
        val jsonString = response.bodyAsText()
        val json = Json.parseToJsonElement(jsonString)

        if (json is JsonObject) {
            val dataArray = json["data"]?.jsonArray ?: return cmcRanks.toList()
            for (item in dataArray) {
                val token = item.jsonObject
                val symbol = token["symbol"]?.jsonPrimitive?.content ?: continue
                val cmcRank = token["cmc_rank"]?.jsonPrimitive?.intOrNull ?: continue
                val usd = token["quote"]?.jsonObject?.get("USD")?.jsonObject ?: continue
                val price =
                    usd["price"]?.jsonPrimitive?.toString()?.toBigDecimalOrNull() ?: continue
                val marketCap = usd["market_cap"]?.jsonPrimitive?.doubleOrNull?.toLong() ?: continue
                cmcRanks.add(
                    TokenData(
                        symbol = symbol,
                        cmcRank = cmcRank,
                        marketCap = marketCap,
                        price = price,
                    )
                )
            }
        }

        return cmcRanks.toList()
    }
}
