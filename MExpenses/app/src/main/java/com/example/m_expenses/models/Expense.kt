package com.example.m_expenses.models

import android.content.ContentValues
import android.provider.BaseColumns
import com.example.m_expenses.db.ExpenseContract

data class Expense(
    val id: Long? = null,
    val tripId: Long,
    val type: String,
    val amount: String,
    val time: String,
    val comment: String = ""
) {


    fun getContentValues(): ContentValues {
        return ContentValues().apply {
            if (id != null) put(BaseColumns._ID, id)
            put(ExpenseContract.ExpenseEntry.COLUMN_NAME_TRIP_ID, tripId)
            put(ExpenseContract.ExpenseEntry.COLUMN_NAME_TYPE, type)
            put(ExpenseContract.ExpenseEntry.COLUMN_NAME_AMOUNT, amount)
            put(ExpenseContract.ExpenseEntry.COLUMN_NAME_TIME, time)
            put(ExpenseContract.ExpenseEntry.COLUMN_NAME_COMMENT, comment)
        }
    }
}