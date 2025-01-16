package com.compose.quickapi.domain.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.quickapi.data.ApiService
import com.compose.quickapi.data.Drink
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DrinksViewModel(private val apiService: ApiService) : ViewModel() {
    private val _drinks = MutableStateFlow<List<Drink>>(emptyList())
    val drinks: StateFlow<List<Drink>> get() = _drinks

    fun fetchDrinksByLetter(letter: String) {
        viewModelScope.launch {
            try {
                val drinksList = apiService.getListByLetter(letter)
                _drinks.value = drinksList
            } catch (e: Exception) {
                _drinks.value = emptyList() // Handle error appropriately
            }
        }
    }
}