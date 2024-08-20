package com.example.expensetracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val userId: String,  // Add this field
    val title: String,
    val amount: Double,
    val date: String,
    val category: String,
    val type: String
)
