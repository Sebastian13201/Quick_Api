package com.compose.quickapi.data

import kotlinx.serialization.Serializable

@Serializable
data class DrinksResponse(
    val drinks: List<Drink>?
)