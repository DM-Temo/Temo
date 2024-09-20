package com.example.temo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TemoViewModel : ViewModel() {
    val userFlow = MutableStateFlow(User(userName = "", userId = ""))

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

    fun updateUser(
        userName: String,
        userId: String
    ) {
        userFlow.value = userFlow.value.copy(
            userName = userName,
            userId = userId
        )
    }
}

data class User(
    var userName: String,
    var userId: String
)