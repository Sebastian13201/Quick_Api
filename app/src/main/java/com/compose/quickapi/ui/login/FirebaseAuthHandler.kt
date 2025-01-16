package com.compose.quickapi.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.compose.quickapi.viewmodels.AuthViewModel


@Composable
fun FirebaseAuthHandler(
    authViewModel: AuthViewModel,
    onAuthenticated: @Composable () -> Unit,
    onUnauthenticated: @Composable () -> Unit
) {
    val user = authViewModel.currentUser.collectAsState().value
    if (user != null) {
        onAuthenticated()
    } else {
        onUnauthenticated()
    }
}