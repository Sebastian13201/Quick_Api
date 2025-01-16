package com.compose.quickapi.presentation.ui.drinks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.compose.quickapi.data.ApiService
import com.compose.quickapi.data.Drink
import com.compose.quickapi.domain.viewmodels.AuthViewModel
import com.compose.quickapi.domain.viewmodels.DrinksViewModel
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrinkList(
    drinks: List<Drink>,
    authViewModel: AuthViewModel,
    viewModel: DrinksViewModel,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf("All") }

    // Filter drinks based on the selected filter and search query
    val filteredDrinks = drinks.filter { drink ->
        (selectedFilter == "All" || drink.strCategory == selectedFilter) &&
                drink.strDrink.contains(searchQuery, ignoreCase = true)
    }

    val categories = drinks.map { it.strCategory }.distinct() + "All"

    Scaffold(
        modifier = Modifier.padding(top = 8.dp),
        topBar = {
            Column {
                TopAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Drink List", style = MaterialTheme.typography.headlineMedium)
                            IconButton(onClick = { expanded = true }) {
                                Icon(Icons.Filled.Edit, contentDescription = "Filter")
                            }
                        }
                    }
                )
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search drinks") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(('A'..'Z').toList()) { letter ->
                        Text(
                            text = letter.toString(),
                            modifier = Modifier
                                .clickable {
                                    viewModel.fetchDrinksByLetter(letter.toString())
                                }
                                .padding(8.dp),
                        )
                    }
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category) },
                            onClick = {
                                selectedFilter = category
                                expanded = false
                            }
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { authViewModel.signOut() },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout")
            }
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                items(filteredDrinks) { drink ->
                    DrinkCard(
                        drinkName = drink.strDrink,
                        category = drink.strCategory,
                        glassType = drink.strGlass,
                        imageUrl = drink.strDrinkThumb
                    )
                }
            }
        }
    )
}