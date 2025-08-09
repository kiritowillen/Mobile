package com.example.mobile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExternalNavigationViewModel:ViewModel() {
    private val _isLoggedIn = MutableStateFlow(FirebaseAuth.getInstance().currentUser != null)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    init {
        FirebaseAuth.getInstance().addAuthStateListener { auth ->
            _isLoggedIn.value = auth.currentUser != null
        }
    }

    fun logout() {
        viewModelScope.launch {
            FirebaseAuth.getInstance().signOut()
        }
    }
}