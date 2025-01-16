package com.compose.quickapi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.compose.quickapi.domain.viewmodels.AuthViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import io.mockk.*
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: AuthViewModel
    private lateinit var mockUser: FirebaseUser
    private lateinit var mockAuthResult: AuthResult

    @Before
    fun setUp() {
        // Mock FirebaseAuth and FirebaseUser
        auth = mockk(relaxed = true)
        mockUser = mockk(relaxed = true)
        mockAuthResult = mockk(relaxed = true)
        Dispatchers.setMain(Dispatchers.Unconfined)

        // Set up the ViewModel with the mocked FirebaseAuth
        viewModel = AuthViewModel(auth)

        // Mock the current user
        every { auth.currentUser } returns mockUser
        every { mockUser.email } returns "ceb@gmail.com"
    }

    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun testInitialUserAuthenticationState() {
        // The user should be authenticated initially
        assertTrue(viewModel.isUserAuthenticated.value)
    }

    @Test
    fun testSignOut() {
        // Execute the signOut method
        viewModel.signOut()

        // Verify that the current user is null and authenticated state is false
        assertFalse(viewModel.currentUser.value != null)
        assertFalse(viewModel.isUserAuthenticated.value)
    }

    @Test
    fun testSignInSuccess() = runBlocking {
        // Mock the sign-in process
        val mockTask: Task<AuthResult> = mockk(relaxed = true)
        every { auth.signInWithEmailAndPassword("ceb@gmail.com", "ceb123") } returns mockTask

        // Simulate a successful sign-in
        every { mockTask.isSuccessful } returns true
        every { mockTask.result } returns mockAuthResult // Return the mocked AuthResult

        every { mockTask.addOnSuccessListener(any()) } answers {
            val listener = firstArg<(AuthResult) -> Unit>()
            listener(mockAuthResult) // Pass the mocked AuthResult
            mockTask
        }

        // Execute the signIn method
        viewModel.signIn("ceb@gmail.com", "ceb123") { }

        // Verify that user is authenticated
        assertTrue(viewModel.isUserAuthenticated.value)
    }

//    @Test
//    fun testSignInFailure() = runBlocking {
//        val mockTask: Task<AuthResult> = mockk(relaxed = true)
//        every { auth.signInWithEmailAndPassword("ceb@gmail.com", "wrongpassword") } returns mockTask
//
//        every { mockTask.isSuccessful } returns false
//        every { mockTask.addOnFailureListener(any()) } answers {
//            val listener = firstArg<(Exception) -> Unit>()
//            listener.invoke(Exception("Login Failed")) // Simulate failure
//            mockTask
//        }
//
//        var errorMessage = ""
//        viewModel.signIn("ceb@gmail.com", "wrongpassword") {
//            errorMessage = it
//        }
//
//        // Log the error message for debugging
//        println("Error message received: $errorMessage")
//
//        // Verify the error message
//        assertTrue("Expected error message not received", errorMessage.contains("Login Failed"))
//    }

    @Test
    fun testSignUpSuccess() = runBlocking {
        // Mock the sign-up process
        val mockTask: Task<AuthResult> = mockk(relaxed = true)
        every { auth.createUserWithEmailAndPassword("newceb@gmail.com", "ceb123") } returns mockTask

        // Simulate a successful sign-up
        every { mockTask.isSuccessful } returns true
        every { mockTask.result } returns mockAuthResult // Return the mocked AuthResult

        every { mockTask.addOnSuccessListener(any()) } answers {
            val listener = firstArg<(AuthResult) -> Unit>()
            listener(mockAuthResult) // Pass the mocked AuthResult
            mockTask
        }

        viewModel.signUp("newceb@gmail.com", "ceb123") { }

        // Verify that user is authenticated
        assertTrue(viewModel.isUserAuthenticated.value)
    }

//    @Test
//    fun testSignUpFailure() = runBlocking {
//        // Mock the sign-up failure
//        val mockTask: Task<AuthResult> = mockk(relaxed = true)
//        every { auth.createUserWithEmailAndPassword("newceb@gmail.com", "ceb123") } returns mockTask
//
//        every { mockTask.isSuccessful } returns false
//        every { mockTask.addOnFailureListener(any()) } answers {
//            val listener = firstArg<(Exception) -> Unit>()
//            listener.invoke(Exception("SignUp Failed")) // Simulate failure
//            mockTask
//        }
//
//        var errorMessage = ""
//        viewModel.signUp("newceb@gmail.com", "ceb123") {
//            errorMessage = it
//        }
//
//        // Verify the error message
//        assertTrue(errorMessage.contains("SignUp Failed"))
//    }
}