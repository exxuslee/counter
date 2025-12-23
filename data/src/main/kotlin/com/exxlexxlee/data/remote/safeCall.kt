package com.exxlexxlee.data.remote

import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.bodyAsText

suspend fun <T> safeCall(block: suspend () -> T): T {
    return try {
        block()
    } catch (_: HttpRequestTimeoutException) {
        throw Exception("Request timed out")
    } catch (e: ResponseException) {
        val errorBody = e.response.bodyAsText()
        throw Exception("url=${e.response.call.request.url} status=${e.response.status} | $errorBody")
    } catch (e: Exception) {
        println("safeCall: ${e.localizedMessage}")
        throw e
    }
}