package com.compose.quickapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.compose.quickapi.ui.drinks.DrinkList
import com.compose.quickapi.ui.login.FirebaseAuthHandler
import com.compose.quickapi.ui.login.LoginScreen
import com.compose.quickapi.ui.theme.QuickApiTheme
import com.compose.quickapi.viewmodels.AuthViewModel
import com.compose.quickapi.viewmodels.DrinksViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
//    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        firebaseAuth = FirebaseAuth.getInstance()

        enableEdgeToEdge()
        setContent {
            QuickApiTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val authViewModel: AuthViewModel by viewModel()
                    val drinksViewModel: DrinksViewModel by viewModel()
                    val drinks = drinksViewModel.drinks.collectAsState().value
                    val isUserAuthenticated by authViewModel.isUserAuthenticated.collectAsState()

                    LaunchedEffect(Unit) {
                        if (isUserAuthenticated) {
                            drinksViewModel.fetchDrinks()
                        }
                    }

                    if (isUserAuthenticated) {
                        DrinkList(
                            drinks = drinks,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }else {
                        LoginScreen(
                            authViewModel = authViewModel,
                        )
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