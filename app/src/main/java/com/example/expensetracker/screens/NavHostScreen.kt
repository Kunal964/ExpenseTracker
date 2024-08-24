package com.example.expensetracker.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
@Composable
fun NavHostScreen(navController: NavHostController, userId: String) {
    // This NavHost controls the navigation within the main app after login
    NavHost(navController = navController, startDestination = "bottomBar") {
        composable(route = "bottomBar") {
            BottomBarScreen(navController, userId = userId)
        }
        composable(route = "/home") {
            HomeScreen(userId = userId)
        }
        composable(route = "/add") {
            AddExpense(navController, userId = userId)
        }
        composable(route = "/person") {
            PersonScreen(navController)
        }
    }
}

