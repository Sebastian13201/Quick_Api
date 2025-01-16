package com.compose.quickapi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.compose.quickapi.data.ApiService
import com.compose.quickapi.data.Drink
import com.compose.quickapi.viewmodels.DrinksViewModel
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DrinksViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var apiService: ApiService
    private lateinit var viewModel: DrinksViewModel

    @Before
    fun setUp() {
        // Mock ApiService
        apiService = mockk()
        viewModel = DrinksViewModel(apiService)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun testFetchDrinksUpdatesState(): Unit = runTest {
        // Prepare mock data
        val mockDrinks = listOf(
            Drink("1", "Beer", "Beer", "Beer", "Beer"),
            Drink("2", "Wine", "Wine", "Wine", "Wine")
        )

        // Mock the behavior of the apiService.getDrinks() method
        coEvery { apiService.getDrinks() } returns mockDrinks

        // Fetch drinks
        viewModel.fetchDrinks()

        // Verify that the drinks state is updated
        assertEquals(mockDrinks, viewModel.drinks.value)
    }

    @Test
    fun testFetchDrinksWithEmptyResponse(): Unit = runTest {
        // Mock the behavior of the apiService.getDrinks() method to return an empty list
        coEvery { apiService.getDrinks() } returns emptyList()

        // Fetch drinks
        viewModel.fetchDrinks()

        // Verify that the drinks state is empty
        assertEquals(emptyList<Drink>(), viewModel.drinks.value)
    }
}