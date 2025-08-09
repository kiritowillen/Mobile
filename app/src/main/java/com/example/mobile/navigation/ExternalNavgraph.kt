package com.example.mobile.navigation

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobile.ExternalNavigationViewModel
import com.example.mobile.ui.Applicazione
import com.example.mobile.ui.screens.login.LoginScreen

@SuppressLint("ContextCastToActivity")
@Composable
fun ExternalNavGraph(
    navController: NavHostController,
    externalnNavViewModel: ExternalNavigationViewModel
) {
    val isLoggedIn by externalnNavViewModel.isLoggedIn.collectAsState()

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) "home" else "login"
    ) {
        composable("login") {
            val activity = LocalContext.current as Activity
            LoginScreen(
                activity = activity,
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("home") {
            Applicazione(
                onLogout = {
                    externalnNavViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }
    }
}
