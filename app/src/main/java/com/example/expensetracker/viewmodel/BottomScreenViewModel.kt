package com.example.expensetracker.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.Navigation.PostOfficeAppRouter
import com.example.expensetracker.Navigation.Screen
import com.example.expensetracker.data.ExpenseDataBase
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
            getUserData(userId)
        //    fetchExpensesFromFirestore()
        } else {
            Log.d(TAG, "No user logged in")
            isUserLoggedIn.value = false
            clearUserData()
        }
    }

    init {
        firebaseAuth.addAuthStateListener(authStateListener)
        checkForActiveSession()
        getUserData(userId)
    }

    override fun onCleared() {
        super.onCleared()
        firebaseAuth.removeAuthStateListener(authStateListener)
    }

    fun logout() {
//            saveExpensesToFirestore()  // Save expenses to Firestore before logging out
            clearUserData()  // clear user data before signing out
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

//    private fun fetchExpensesFromFirestore() {
//        viewModelScope.launch {
//            firestore.collection("expenses")
//                .whereEqualTo("userId", userId)
//                .get()
//                .addOnSuccessListener { result ->
//                    val expenses = result.documents.mapNotNull { document ->
//                        document.toObject(ExpenseEntity::class.java)
//                    }
//                    insertExpensesIntoRoom(expenses)   // Update Room with fetched data
//                }
//                .addOnFailureListener { exception ->
//                    Log.d(TAG, "Error fetching expenses: ", exception)
//                }
//        }
//    }
//
//    private fun insertExpensesIntoRoom(expenses: List<ExpenseEntity>) {
//        viewModelScope.launch {
//            dao.insertAll(expenses)
//        }
//    }
//
//    private fun saveExpensesToFirestore() {
//        viewModelScope.launch {
//            try {
//                val expenses: List<ExpenseEntity> = dao.getAllExpenses(userId) // Make sure it's a list
//                for (expense in expenses) {
//                    firestore.collection("expenses").document(expense.id.toString())
//                        .set(expense)
//                        .addOnSuccessListener {
//                            Log.d(TAG, "Expense saved to Firestore: ${expense.id}")
//                        }
//                        .addOnFailureListener { exception ->
//                            Log.e(TAG, "Error saving expense to Firestore: ", exception)
//                        }
//                }
//            } catch (e: Exception) {
//                Log.e(TAG, "Error retrieving expenses: ${e.message}", e)
//            }
//        }
//    }




    fun checkForActiveSession() {
        if (firebaseAuth.currentUser != null) {
            Log.d(TAG, "Valid session for userId: $userId")
            isUserLoggedIn.value = true
        } else {
            Log.d(TAG, "No active session for userId: $userId")
            isUserLoggedIn.value = false
        }
    }

    private fun getUserData(userId: String) {
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

    private fun clearUserData() {
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
