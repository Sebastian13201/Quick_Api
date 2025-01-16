package com.compose.quickapi.ui.drinks

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.compose.quickapi.data.Drink

@Composable
fun DrinkList(drinks: List<Drink>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(drinks) { drink ->
            DrinkCard(
                drinkName = drink.idDrink,
                category = drink.strCategory,
                glassType = drink.strGlass,
                imageUrl = drink.strDrinkThumb
            )
        }
    }
}