package com.example.expensetracker.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.data.NavItem

@Composable
fun BottomBarScreen(navController: NavController) {
    val navItemList = listOf(
        NavItem("Home", Icons.Default.Home),
        NavItem("Add", Icons.Default.AddCircle),
        NavItem("Person", Icons.Default.Person)
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
                            selectedIndex = index
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
            selectedIndex = selectedIndex
        )
    }
}

@Composable
fun ContentScreen(navController: NavController, modifier: Modifier = Modifier, selectedIndex: Int) {
    when (selectedIndex) {
        0 -> HomeScreen(navController = navController)
        1 -> AddExpense(navController = navController)
        2 -> PersonScreen(navController = navController)
        // Add additional cases if there are more items in the bottom bar
    }
}

@Preview
@Composable
fun BottomPreview() {
    BottomBarScreen(rememberNavController())
}
