package com.example.expensetracker.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.Navigation.PostOfficeAppRouter
import com.example.expensetracker.Navigation.Screen
import com.example.expensetracker.data.NavItem
import com.example.expensetracker.viewmodel.BottomScreenViewModel
import com.example.expensetracker.viewmodel.BottomScreenViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun BottomBarScreen(navController: NavController, userId: String) {
    val context = LocalContext.current
    val firebaseAuth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    val viewModel: BottomScreenViewModel = viewModel(
        factory = BottomScreenViewModelFactory(userId, firebaseAuth, firestore, context)
    )

    val isUserLoggedIn by viewModel.isUserLoggedIn.observeAsState(false)

    if (!isUserLoggedIn) {
        // Navigate back to login screen if not logged in
        PostOfficeAppRouter.navigateTo(Screen.LoginScreen)
    }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val navItemList = listOf(
        NavItem("Home", Icons.Default.Home),
        NavItem("Add", Icons.Default.AddCircle),
        NavItem("Person", Icons.Default.Person),
        NavItem("Logout", Icons.AutoMirrored.Filled.Logout)
    )
    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFFA58FC2)
            ) {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = {
                            if (navItem.label == "Logout") {
                                viewModel.logout()
                            } else {
                                selectedIndex = index
                            }
                        },
                        icon = {
                            Icon(imageVector = navItem.icon, contentDescription = "Icons")
                        },
                        label = {
                            Text(text = navItem.label)
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        ContentScreen(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            selectedIndex = selectedIndex,
            userId = userId
        )
    }
}

@Composable
fun ContentScreen(navController: NavController, modifier: Modifier = Modifier, selectedIndex: Int, userId: String) {
    when (selectedIndex) {
        0 -> HomeScreen(userId = userId, modifier = modifier)
        1 -> AddExpense(navController = navController, userId = userId)
        2 -> PersonScreen(navController = navController)
    }
}

@Preview
@Composable
fun BottomPreview() {
    val mockUserId = "mockUserId123"
    BottomBarScreen(rememberNavController(), userId = mockUserId)
}
