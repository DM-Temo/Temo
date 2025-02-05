package com.example.temo.screens.navigation

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class NavViewModel : ViewModel() {
    private val _currentRoute = MutableStateFlow<String?>(null)
    val currentRoute: StateFlow<String?> = _currentRoute

    fun navigateTo(route: String, navController: NavController) {
        if (_currentRoute.value != route) {
            _currentRoute.value = route
            navController.navigate(route) {
                popUpTo(route) { inclusive = false }
                launchSingleTop = true
            }
        }
    }
}