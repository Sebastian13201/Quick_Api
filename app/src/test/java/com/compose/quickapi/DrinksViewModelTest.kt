package com.compose.quickapi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.compose.quickapi.data.ApiService
import com.compose.quickapi.data.Drink
import com.compose.quickapi.domain.viewmodels.DrinksViewModel
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DrinksViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var apiService: ApiService
    private lateinit var viewModel: DrinksViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)

        // Mock ApiService with relaxed = true to avoid unnecessary strict mocking
        apiService = mockk(relaxed = true)
        viewModel = DrinksViewModel(apiService)
    }

    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun testFetchDrinksByLetterUpdatesState() = runTest {
        // Prepare mock data for drinks starting with letter "A"
        val mockDrinks = listOf(
            Drink("1", "Apple Martini", "Cocktail", "Cocktail glass", "url_to_image"),
            Drink("2", "Amaretto Sour", "Cocktail", "Old-fashioned glass", "url_to_image")
        )

        // Mock the behavior of the apiService.getListByLetter() method
        coEvery { apiService.getListByLetter("A") } returns mockDrinks

        // Fetch drinks by letter "A"
        viewModel.fetchDrinksByLetter("A")

        // Need to wait for the coroutine to complete and state to update
        advanceUntilIdle()

        // Verify that the drinks state is updated
        assertEquals(mockDrinks, viewModel.drinks.value)
    }

    @Test
    fun testFetchDrinksByLetterWithEmptyResponse() = runTest {
        // Mock the behavior of the apiService.getListByLetter() method to return an empty list
        coEvery { apiService.getListByLetter("A") } returns emptyList()

        // Fetch drinks by letter "A"
        viewModel.fetchDrinksByLetter("A")

        // Need to wait for the coroutine to complete and state to update
        advanceUntilIdle()

        // Verify that the drinks state is empty
        assertTrue(viewModel.drinks.value?.isEmpty() ?: false)
    }

    @Test
    fun testFetchDrinksByLetterHandlesError() = runTest {
        // Mock the behavior of the apiService.getListByLetter() method to throw an exception
        coEvery { apiService.getListByLetter("A") } throws Exception("Network error")

        // Fetch drinks by letter "A"
        viewModel.fetchDrinksByLetter("A")

        // Need to wait for the coroutine to complete and state to update
        advanceUntilIdle()

        // Verify that the drinks state is empty or in an error state
        assertTrue(viewModel.drinks.value?.isEmpty() ?: true)

        // If you have an error state in your ViewModel, you should verify it here
        // For example: assertTrue(viewModel.error.value?.contains("Network error") ?: false)
    }
}