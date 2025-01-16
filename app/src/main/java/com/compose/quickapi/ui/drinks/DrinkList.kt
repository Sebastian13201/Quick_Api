package com.compose.quickapi.ui.drinks

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.compose.quickapi.data.Drink
import com.compose.quickapi.viewmodels.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrinkList(drinks: List<Drink>,
              authViewModel: AuthViewModel,
              modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Drink List") },
                actions = {
                    // Filter Icon
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { authViewModel.signOut() },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Text("Logout")
            }
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
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
    )
}