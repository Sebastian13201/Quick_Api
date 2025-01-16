package com.compose.quickapi.data

import kotlinx.serialization.Serializable

@Serializable
data class Drink(
    val idDrink: String,
    val strDrink: String,
    val strCategory: String,
    val strGlass: String,
    val strDrinkThumb: String
)