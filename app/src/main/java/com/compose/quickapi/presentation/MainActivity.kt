package com.compose.quickapi.presentation

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
import com.compose.quickapi.presentation.ui.drinks.DrinkList
import com.compose.quickapi.presentation.ui.login.LoginScreen
import com.compose.quickapi.presentation.ui.theme.QuickApiTheme
import com.compose.quickapi.domain.viewmodels.AuthViewModel
import com.compose.quickapi.domain.viewmodels.DrinksViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModel()
    private val drinksViewModel: DrinksViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuickApiTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val drinks by drinksViewModel.drinks.collectAsState()
                    val isUserAuthenticated by authViewModel.isUserAuthenticated.collectAsState()
                    var isLoading by remember { mutableStateOf(true) }

                    LaunchedEffect(isUserAuthenticated) {
                        if (isUserAuthenticated) {
                            drinksViewModel.fetchDrinksByLetter("A")
                        }
                        isLoading = false
                    }

                    // Log authentication status
                    Log.d("MainActivity", "User authenticated: $isUserAuthenticated")

                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.padding(innerPadding))
                    } else if (isUserAuthenticated) {
                        DrinkList(
                            drinks = drinks,
                            modifier = Modifier.padding(innerPadding),
                            authViewModel = authViewModel,
                            viewModel = drinksViewModel
                        )
                    } else {
                        LoginScreen(authViewModel = authViewModel)
                    }
                }
            }
        }
    }
}