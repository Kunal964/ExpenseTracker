package com.example.expensetracker.App

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.Navigation.PostOfficeAppRouter
import com.example.expensetracker.Navigation.Screen
import com.example.expensetracker.screens.*
import com.example.expensetracker.viewmodel.BottomScreenViewModel
import com.example.expensetracker.viewmodel.BottomScreenViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun PostOfficeApp(userId: String) {
    val context = LocalContext.current
    val firebaseAuth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    val bottomViewModel: BottomScreenViewModel = viewModel(
        factory = BottomScreenViewModelFactory(userId, firebaseAuth, firestore, context)
    )

    val navController = rememberNavController()

    bottomViewModel.checkForActiveSession()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            if (bottomViewModel.isUserLoggedIn.value == true) {
                PostOfficeAppRouter.navigateTo(Screen.BottomBarScreen(userId = userId))
            }

            Crossfade(targetState = PostOfficeAppRouter.currentScreen) { currentScreen ->
                when (currentScreen.value) {
                    is Screen.SignUpScreen -> SignUpScreen()
                    is Screen.TermsAndConditionScreen -> TermsAndConditionScreen()
                    is Screen.LoginScreen -> LoginScreen()
                    is Screen.BottomBarScreen -> NavHostScreen(navController = navController, userId = userId)
                }
            }
        }
    }
}
