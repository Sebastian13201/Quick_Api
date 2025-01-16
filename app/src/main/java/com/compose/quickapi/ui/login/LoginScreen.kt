package com.compose.quickapi.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.compose.quickapi.viewmodels.AuthViewModel

@Composable
fun LoginScreen(authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

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
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                authViewModel.signIn(email, password) {
                    errorMessage = it
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign In")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                authViewModel.signUp(email, password) {
                    errorMessage = it
                }
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
    LoginScreen(authViewModel = AuthViewModel(
        firebaseAuth = com.google.firebase.auth.FirebaseAuth.getInstance()
    ))
}