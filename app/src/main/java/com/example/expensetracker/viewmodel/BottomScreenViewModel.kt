package com.example.expensetracker.viewmodel

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.Navigation.PostOfficeAppRouter
import com.example.expensetracker.Navigation.Screen
import com.example.expensetracker.data.NavigationItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class BottomScreenViewModel(
    private val userId: String,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val TAG = BottomScreenViewModel::class.simpleName

    val navigationItemsList = listOf(
        NavigationItem(
            title = "Home",
            icon = Icons.Default.Home,
            description = "Home Screen",
            itemId = "homeScreen",
        ),
        NavigationItem(
            title = "Settings",
            icon = Icons.Default.Settings,
            description = "Settings Screen",
            itemId = "settingsScreen"
        ),
        NavigationItem(
            title = "Favorite",
            icon = Icons.Default.Favorite,
            description = "Favorite Screen",
            itemId = "favoriteScreen"
        )
    )

    val isUserLoggedIn: MutableLiveData<Boolean> = MutableLiveData()
    val emailId: MutableLiveData<String?> = MutableLiveData()

    init {
        checkForActiveSession()
        getUserData()
    }

    fun logout() {
        firebaseAuth.signOut()

        val authStateListener = FirebaseAuth.AuthStateListener {
            if (it.currentUser == null) {
                Log.d(TAG, "Sign out successful")
                PostOfficeAppRouter.navigateTo(Screen.LoginScreen)
            } else {
                Log.d(TAG, "Sign out failed")
            }
        }

        firebaseAuth.addAuthStateListener(authStateListener)
    }

    fun checkForActiveSession() {
        if (firebaseAuth.currentUser != null) {
            Log.d(TAG, "Valid session for userId: $userId")
            isUserLoggedIn.value = true
        } else {
            Log.d(TAG, "No active session for userId: $userId")
            isUserLoggedIn.value = false
        }
    }

    private fun getUserData() {
        firestore.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val email = document.getString("email")
                    emailId.value = email
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting document: ", exception)
            }
    }
}

class BottomScreenViewModelFactory(
    private val userId: String,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BottomScreenViewModel::class.java)) {
            return BottomScreenViewModel(userId, firebaseAuth, firestore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
