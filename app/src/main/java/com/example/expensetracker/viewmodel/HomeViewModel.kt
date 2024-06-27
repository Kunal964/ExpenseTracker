package com.example.expensetracker.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.R
import com.example.expensetracker.Utils
import com.example.expensetracker.data.ExpenseDataBase
import com.example.expensetracker.data.dao.ExpenseDao
import com.example.expensetracker.data.model.ExpenseEntity

class HomeViewModel(dao: ExpenseDao): ViewModel() {
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
        return "$ ${Utils.formatToDecimalValue(balance)}"
    }

    fun getTotalExpense(list: List<ExpenseEntity>) : String {
        var total = 0.0
        list.forEach{
            if (it.type == "Expense") {
                total += it.amount
            }
            else {
                total -= it.amount
            }
        }
        return "$ ${Utils.formatToDecimalValue(total)}"



    }
    fun getTotalIncome(list: List<ExpenseEntity>) : String {
        var totalIncome = 0.0
        list.forEach{
            if (it.type == "Income") {
                totalIncome += it.amount
            }
            else {
                totalIncome -= it.amount
            }
        }
        return "$ ${Utils.formatToDecimalValue(totalIncome)}"
    }

    // Here I'll add the icon of the category

    fun getItemIcon(item: ExpenseEntity): Int {
        if(item.category == "Youtube")
            return R.drawable.ic_youtube
        else if (item.category == "Netflix")
            return R.drawable.ic_netflix
        else if (item.category == "Google")
            return  R.drawable.ic_google
        return R.drawable.ic_paytm
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