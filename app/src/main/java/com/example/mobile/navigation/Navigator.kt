package com.example.mobile.navigation

import androidx.navigation.NavHostController
/*
La claase navigator ha il ruoto di mantenere al so interno il HostNavController e di gestire lo stack
*/
class Navigator(private val _navController: NavHostController) {

    fun navigateClearingStack(route: String) {
        navController.navigate(route) {
            popUpTo(0) { inclusive = true }
            launchSingleTop = true
        }
    }

    val navController: NavHostController
        get() = _navController

    fun goBack() {
        navController.popBackStack()
    }

    fun navigateTo(route: String) {
        navController.navigate(route)
    }


}