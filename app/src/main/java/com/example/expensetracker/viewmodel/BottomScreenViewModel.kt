package com.example.expensetracker.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.Navigation.PostOfficeAppRouter
import com.example.expensetracker.Navigation.Screen
import com.example.expensetracker.data.ExpenseDataBase
import com.example.expensetracker.data.NavigationItem
import com.example.expensetracker.data.dao.ExpenseDao
import com.example.expensetracker.data.model.ExpenseEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class BottomScreenViewModel(
    private var userId: String,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val dao: ExpenseDao
) : ViewModel() {

    private val TAG = BottomScreenViewModel::class.simpleName

    val isUserLoggedIn: MutableLiveData<Boolean> = MutableLiveData()
    val emailId: MutableLiveData<String?> = MutableLiveData()

    private val authStateListener = FirebaseAuth.AuthStateListener { auth ->
        val currentUser = auth.currentUser
        if (currentUser != null) {
            userId = currentUser.uid
            Log.d(TAG, "User logged in with userId: $userId")
            isUserLoggedIn.value = true
            getUserData()
        } else {
            Log.d(TAG, "No user logged in")
            isUserLoggedIn.value = false
            clearUserData()
        }
    }

    init {
        firebaseAuth.addAuthStateListener(authStateListener)
        checkForActiveSession()
        getUserData()
    }

    override fun onCleared() {
        super.onCleared()
        firebaseAuth.removeAuthStateListener(authStateListener)
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

    fun clearUserData() {
        viewModelScope.launch {
            dao.deleteAll(userId)
        }
    }
}




class BottomScreenViewModelFactory(
    private val userId: String,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val context: Context
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BottomScreenViewModel::class.java)) {
            val dao = ExpenseDataBase.getDatabase(context).expenseDao()
            return BottomScreenViewModel(userId, firebaseAuth,  firestore, dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
