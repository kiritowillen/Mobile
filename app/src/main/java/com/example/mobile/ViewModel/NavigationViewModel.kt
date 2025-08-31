package com.example.mobile.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mobile.data.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NavigationViewModel : ViewModel() {
    // Stato della schermata corrente
    private val _currentScreen = MutableStateFlow<Screen>(Screen.Tastiera)  // Default è Tastiera
    val currentScreen: StateFlow<Screen> = _currentScreen

    // Funzione per navigare verso una schermata
    fun navigateTo(screen: Screen) {
        _currentScreen.value = screen
        stampa(screen = screen)
    }

    fun stampa(screen: Screen){
        Log.d("NavigationViewModel", "Navigating to screen: ${screen.route}")
    }
}
