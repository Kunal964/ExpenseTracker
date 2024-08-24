package com.example.expensetracker.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.R
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.Navigation.PostOfficeAppRouter
import com.example.expensetracker.Navigation.Screen
import kotlinx.coroutines.launch
import com.example.expensetracker.Utils
import com.example.expensetracker.data.ExpenseDataBase
import com.example.expensetracker.data.dao.ExpenseDao
import com.example.expensetracker.data.model.ExpenseEntity
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel(
    private val dao: ExpenseDao,
    val userId: String
): ViewModel() {
    private val TAG = HomeViewModel::class.simpleName
    val expenses = dao.getAllExpense(userId = userId)

    fun deleteExpense(expenseEntity: ExpenseEntity) {
        viewModelScope.launch {
            dao.deleteExpense(expenseEntity)
        }
    }

    fun getBalance(list: List<ExpenseEntity>) : String {
        var balance = 0.0
        list.forEach{
            if (it.type == "Income") {
                balance += it.amount
            }
            else {
                balance -= it.amount
            }
        }
        return "₹ ${Utils.formatToDecimalValue(balance)}"
    }

    fun getTotalExpense(list: List<ExpenseEntity>) : String {
        var total = 0.0
        list.forEach{
            if (it.type == "Expense") {
                total += it.amount
            }
        }
        return "₹ ${Utils.formatToDecimalValue(total)}"
    }

    fun getTotalIncome(list: List<ExpenseEntity>) : String {
        var totalIncome = 0.0
        list.forEach{
            if (it.type == "Income") {
                totalIncome += it.amount
            }
        }
        return "₹ ${Utils.formatToDecimalValue(totalIncome)}"
    }

    // Here I'll add the icon of the category

    fun getItemIcon(item: ExpenseEntity): Int {
        return when (item.category) {
            "Youtube" -> R.drawable.ic_youtube
            "Netflix" -> R.drawable.ic_netflix
            "Google" -> R.drawable.ic_google
            "Person" -> R.drawable.person
            "Salary" -> R.drawable.money
            else -> R.drawable.ic_paytm
        }
    }

    fun clearTransactions() {
        viewModelScope.launch {
            dao.deleteAll(userId = userId)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val context: Context, private val userId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            val dao = ExpenseDataBase.getDatabase(context).expenseDao()
            return HomeViewModel(dao, userId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


