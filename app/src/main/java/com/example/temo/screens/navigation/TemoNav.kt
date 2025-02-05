package com.example.temo.screens.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.temo.Screen
import com.example.temo.screens.AddScreen
import com.example.temo.screens.DetailScreen
import com.example.temo.screens.HomeScreen
import com.example.temo.screens.ProfileScreen
import com.example.temo.viewmodels.TemoViewModel

@Composable
fun TemoNavHost(
    navController: NavHostController,
    temoViewModel: TemoViewModel,
    navViewModel: NavViewModel,
    innerPadding: PaddingValues
) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                innerPadding = innerPadding.calculateTopPadding(),
                temoViewModel = temoViewModel,
                navViewModel = navViewModel,
                navController = navController
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen(
                innerPadding = innerPadding.calculateTopPadding(),
                temoViewModel = temoViewModel,
                navViewModel = navViewModel,
                navController = navController
            )
        }
        composable(Screen.Add.route) {
            AddScreen(
                innerPadding = innerPadding.calculateTopPadding(),
                temoViewModel = temoViewModel
            )
        }
        composable(Screen.Detail.route) {
            DetailScreen(
                innerPadding = innerPadding.calculateTopPadding(),
                temoViewModel = temoViewModel
            )
        }
    }
}