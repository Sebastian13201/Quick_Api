package com.compose.quickapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.compose.quickapi.ui.drinks.DrinkList
import com.compose.quickapi.ui.theme.QuickApiTheme
import com.compose.quickapi.viewmodels.DrinksViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuickApiTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val drinksViewModel: DrinksViewModel by viewModel()
                    val drinks = drinksViewModel.drinks.collectAsState().value

                    LaunchedEffect(Unit) {
                        drinksViewModel.fetchDrinks()
                    }

                    DrinkList(
                        drinks = drinks,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
