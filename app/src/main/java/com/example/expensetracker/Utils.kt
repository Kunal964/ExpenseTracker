package com.example.expensetracker

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Locale

object Utils {
    @SuppressLint("WeekBasedYear")
    fun fromatDatetoHumanReadableForm(dateInMillis:Long) : String {
        val dateFormatter = SimpleDateFormat("dd/MM/YYYY", Locale.getDefault())
        return dateFormatter.format(dateInMillis)
    }
    @SuppressLint("DefaultLocale")
    fun formatToDecimalValue(d: Double): String {
        return String. format("%.2f",d)
    }
}