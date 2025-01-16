package com.compose.quickapi.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val firebaseAuth: FirebaseAuth) : ViewModel() {
    private val _currentUser = MutableStateFlow<FirebaseUser?>(firebaseAuth.currentUser)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser

    private val _isUserAuthenticated = MutableStateFlow(firebaseAuth.currentUser != null)
    val isUserAuthenticated: StateFlow<Boolean> = _isUserAuthenticated

    fun signIn(email: String, password: String, onError: (String) -> Unit) {
        viewModelScope.launch {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    _currentUser.value = firebaseAuth.currentUser
                }
                .addOnFailureListener {
                    onError(it.localizedMessage ?: "Unknown error")
                }
        }
    }

    fun signUp(email: String, password: String, onError: (String) -> Unit) {
        viewModelScope.launch {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    _currentUser.value = firebaseAuth.currentUser
                }
                .addOnFailureListener {
                    onError(it.localizedMessage ?: "Unknown error")
                }
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
        _currentUser.value = null
    }
}