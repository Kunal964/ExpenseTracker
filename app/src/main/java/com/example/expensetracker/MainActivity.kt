package com.example.expensetracker

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.expensetracker.App.PostOfficeApp
import com.example.expensetracker.screens.NavHostScreen
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "Starting MainActivity")
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            ExpenseTrackerTheme {
                val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "mockUserId123"
                PostOfficeApp(userId = userId)
                // Ensure this is your single source of truth for navigation
            }
        }
    }
}



