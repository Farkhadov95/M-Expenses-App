package com.example.m_expenses.db

import android.provider.BaseColumns
import com.example.m_expenses.db.ExpenseContract.ExpenseEntry.COLUMN_NAME_AMOUNT
import com.example.m_expenses.db.ExpenseContract.ExpenseEntry.COLUMN_NAME_COMMENT
import com.example.m_expenses.db.ExpenseContract.ExpenseEntry.COLUMN_NAME_TIME
import com.example.m_expenses.db.ExpenseContract.ExpenseEntry.COLUMN_NAME_TRIP_ID
import com.example.m_expenses.db.ExpenseContract.ExpenseEntry.COLUMN_NAME_TYPE
import com.example.m_expenses.db.ExpenseContract.ExpenseEntry.TABLE_NAME

object ExpenseContract {

    const val CREATE_EXPENSES_TABLE = "CREATE TABLE $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "$COLUMN_NAME_TRIP_ID INTEGER," +
            "$COLUMN_NAME_TYPE TEXT," +
            "$COLUMN_NAME_AMOUNT TEXT," +
            "$COLUMN_NAME_TIME TEXT," +
            "$COLUMN_NAME_COMMENT TEXT)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"


    object ExpenseEntry : BaseColumns {
        const val TABLE_NAME = "expenses"

        const val COLUMN_NAME_TRIP_ID = "tripId"
        const val COLUMN_NAME_TYPE = "type"
        const val COLUMN_NAME_AMOUNT = "amount"
        const val COLUMN_NAME_TIME = "time"
        const val COLUMN_NAME_COMMENT = "comment"
    }
}