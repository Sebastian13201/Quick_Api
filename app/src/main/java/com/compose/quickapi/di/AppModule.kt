package com.compose.quickapi.di

import com.compose.quickapi.data.ApiService
import com.compose.quickapi.viewmodels.AuthViewModel
import com.compose.quickapi.viewmodels.DrinksViewModel
import com.google.firebase.auth.FirebaseAuth
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { HttpClient(CIO) }
    single { ApiService(get()) }
    viewModel { DrinksViewModel(get()) }
    viewModel { AuthViewModel(get()) }
    single { FirebaseAuth.getInstance() }
}