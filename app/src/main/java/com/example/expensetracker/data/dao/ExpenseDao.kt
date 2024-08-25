package com.example.expensetracker.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.expensetracker.data.model.ExpenseEntity

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expenses WHERE userId = :userId")
    fun getAllExpenseLiveData(userId: String): LiveData<List<ExpenseEntity>>


    // This method returns the list directly
    @Query("SELECT * FROM expenses WHERE userId = :userId")
    suspend fun getAllExpenses(userId: String): List<ExpenseEntity>

    @Insert
    suspend fun insertExpense(expenseEntity: ExpenseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(expenses: List<ExpenseEntity>)

    @Delete
    suspend fun deleteExpense(expenseEntity: ExpenseEntity)

    @Update
    suspend fun updateExpense(expenseEntity: ExpenseEntity)

    @Query("DELETE FROM expenses WHERE userId = :userId")
    suspend fun deleteAll(userId: String)
}
