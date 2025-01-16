package com.compose.quickapi.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.quickapi.data.ApiService
import com.compose.quickapi.data.Drink
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DrinksViewModel(private val apiService: ApiService) : ViewModel() {
    private val _drinks = MutableStateFlow<List<Drink>>(emptyList())
    val drinks: StateFlow<List<Drink>> = _drinks

    // Update to no longer take the letter, use the fixed API endpoint
    fun fetchDrinks() {
        viewModelScope.launch {
            val drinksList = apiService.getDrinks()
            _drinks.value = drinksList
        }
    }
}