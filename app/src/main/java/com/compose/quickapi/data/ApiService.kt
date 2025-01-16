package com.compose.quickapi.data

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json

class ApiService(private val client: HttpClient) {
    private val baseUrl = "https://www.thecocktaildb.com/api/json/v1/1"

    suspend fun getDrinks(): List<Drink> {
        val response: HttpResponse = client.get("$baseUrl/search.php?f=a")
        val responseBody = response.bodyAsText()

        val json = Json { ignoreUnknownKeys = true }
        val apiResponse = json.decodeFromString<DrinksResponse>(responseBody)
        return apiResponse.drinks ?: emptyList()
    }
}