package com.example.expensetracker.screens


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavHostScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "bottomBar") {
        composable(route = "bottomBar") {
            BottomBarScreen(navController)
        }
        composable(route = "/home") {
            HomeScreen(navController)
        }
        composable(route = "/add") {
            AddExpense(navController)
        }
        composable(route = "/person") {
            PersonScreen(navController)
        }
    }
}


