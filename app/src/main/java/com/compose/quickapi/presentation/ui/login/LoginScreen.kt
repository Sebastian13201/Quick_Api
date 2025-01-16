package com.compose.quickapi.presentation.ui.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.compose.quickapi.domain.viewmodels.AuthViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                authViewModel.signIn(email, password, {
                    Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                    errorMessage = it
                })
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign In")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                authViewModel.signUp(email, password, {
                    Toast.makeText(context, "Sign up successful", Toast.LENGTH_SHORT).show()
                    errorMessage = it// Clear error message on successful sign up
                })
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign Up")
        }
        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    // Note: Provide a mock or a test instance of AuthViewModel for the preview.
    LoginScreen(authViewModel = AuthViewModel(firebaseAuth = FirebaseAuth.getInstance()))
}