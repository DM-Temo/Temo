package com.example.temo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch

class TemoViewModel : ViewModel() {
    fun navigateToHome(navController: NavController) {
        viewModelScope.launch {
            navController.navigate("home")
        }
    }

    fun navigateToProfile(navController: NavController) {
        viewModelScope.launch {
            navController.navigate("profile")
        }
    }

    fun navigateToAdd(navController: NavController) {
        viewModelScope.launch {
            navController.navigate("add")
        }
    }

    fun navigateToDetail(navController: NavController) {
        viewModelScope.launch {
            navController.navigate("detail")
        }
    }
}