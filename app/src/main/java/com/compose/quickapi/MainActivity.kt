package com.compose.quickapi

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.compose.quickapi.ui.drinks.DrinkList
import com.compose.quickapi.ui.login.LoginScreen
import com.compose.quickapi.ui.theme.QuickApiTheme
import com.compose.quickapi.viewmodels.AuthViewModel
import com.compose.quickapi.viewmodels.DrinksViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuickApiTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val authViewModel: AuthViewModel by viewModel()
                    val drinksViewModel: DrinksViewModel by viewModel()
                    val drinks = drinksViewModel.drinks.collectAsState().value
                    val isUserAuthenticated by authViewModel.isUserAuthenticated.collectAsState()
                    var isLoading by remember { mutableStateOf(true) }

                    LaunchedEffect(isUserAuthenticated) {
                        isLoading = false
                        if (isUserAuthenticated) {
                            drinksViewModel.fetchDrinks()
                        }
                    }
                    // Log authentication status
                    Log.d("MainActivity", "User authenticated: $isUserAuthenticated")

                    if (isLoading) {
                        CircularProgressIndicator()
                    } else if (isUserAuthenticated) {
                        DrinkList(
                            drinks = drinks,
                            modifier = Modifier.padding(innerPadding),
                            authViewModel = authViewModel
                        )
                    } else {
                        LoginScreen(authViewModel = authViewModel)
                    }
                }
            }
        }
    }
}