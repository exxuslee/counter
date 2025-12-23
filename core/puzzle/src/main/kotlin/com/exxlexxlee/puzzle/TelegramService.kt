package com.exxlexxlee.puzzle

import com.exxlexxlee.core.puzzle.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class TelegramService {
    companion object {
        private const val BASE_URL = "https://api.telegram.org/"

    }

    private val httpClient: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                encodeDefaults = true
                ignoreUnknownKeys = true
                isLenient = true
                coerceInputValues = true
            })
        }
    }

    suspend fun sendMessage(text: String, chatId: Long) {
        if (text.isEmpty()) return
        val requestBody = buildJsonObject {
            put("chat_id", chatId)
            put("text", text)
            put("disable_web_page_preview", true)
        }
        try {
            val response = httpClient.post("${BASE_URL}bot${BuildConfig.TG_TOKEN}/sendMessage") {
                contentType(ContentType.Application.Json)
                setBody(requestBody)
            }
            println("sendMessage: ${response.status} ${response.bodyAsText()}")
        } catch (e: Exception) {
            println("sendMessage: ${e.printStackTrace()}")
        }
    }

}