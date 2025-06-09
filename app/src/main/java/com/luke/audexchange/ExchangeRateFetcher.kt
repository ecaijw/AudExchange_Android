package com.luke.audexchange

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable

suspend fun fetchFromFrankfurter(): List<RateData> {
    return try {
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        val endDate = java.time.LocalDate.now()
        val startDate = endDate.minusDays(30)
        val url = "https://api.frankfurter.app/$startDate..$endDate?from=AUD&to=USD,CNY"

        val response: FrankfurterResponse = client.get(url).body()
        return response.rates.map { (date, rate) ->
            RateData(
                date = date,
                AUDUSD = rate["USD"] ?: 0.0,
                AUDCNY = rate["CNY"] ?: 0.0
            )
        }.sortedByDescending { it.date }
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    }
}

@Serializable
data class FrankfurterResponse(
    val rates: Map<String, Map<String, Double>>
)
