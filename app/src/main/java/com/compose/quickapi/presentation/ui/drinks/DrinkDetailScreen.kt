package com.compose.quickapi.presentation.ui.drinks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.compose.quickapi.data.Drink

@Composable
fun DrinkDetailScreen(drink: Drink, onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = drink.strDrink, style = MaterialTheme.typography.headlineMedium)
        Text(text = "Category: ${drink.strCategory}")
        Text(text = "Glass: ${drink.strGlass}")
        // Other details can be added here
        // For example, an Image, Instructions, etc.
        // Image(painter = rememberImagePainter(drink.strDrinkThumb), contentDescription = null)

        // Back button
        IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
    }
}