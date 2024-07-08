package com.example.expensetracker.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.R
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.expensetracker.Utils
import com.example.expensetracker.data.ExpenseDataBase
import com.example.expensetracker.data.dao.ExpenseDao
import com.example.expensetracker.data.model.ExpenseEntity

class HomeViewModel(private val dao: ExpenseDao): ViewModel() {
    val expenses = dao.getAllExpense()

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
            dao.deleteAll()
        }
    }
}

class HomeViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            val dao = ExpenseDataBase.getDatabase(context).expenseDao()
            return HomeViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
