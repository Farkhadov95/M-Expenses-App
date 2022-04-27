package com.example.m_expenses.db

import android.content.Context
import android.provider.BaseColumns
import android.util.Log
import com.example.m_expenses.models.Expense

class ExpenseDao private constructor(context: Context) : BaseDao(context) {


    companion object {
        private const val TAG = "ExpenseDao"
        const val TABLE_NAME = ExpenseContract.ExpenseEntry.TABLE_NAME

        private var INSTANCE: ExpenseDao? = null
        fun getInstance(context: Context): ExpenseDao {
            if (INSTANCE == null) INSTANCE = ExpenseDao(context)
            return INSTANCE!!
        }
    }

    override fun clear() {
        super.clear()
        INSTANCE = null
    }

    fun saveExpense(expense: Expense) {
        val count = writableDatabase.insert(
            TABLE_NAME,
            null,
            expense.getContentValues()
        )
        Log.i(TAG, "saveTrip: $count")
    }

    fun getExpenses(tripId: Long): List<Expense> {
        val expenseList = mutableListOf<Expense>()


        val selection = "${ExpenseContract.ExpenseEntry.COLUMN_NAME_TRIP_ID} = ?"
        val selectionArgs = arrayOf("$tripId")

        val cursor = readableDatabase.query(
            TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            "${BaseColumns._ID} DESC"
        )

        with(cursor) {
            while (moveToNext()) {
                val expense = Expense(
                    id = getLong(getColumnIndexOrThrow(BaseColumns._ID)),
                    tripId = getLong(getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.COLUMN_NAME_TRIP_ID)),
                    type = getString(getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.COLUMN_NAME_TYPE)),
                    amount = getString(getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.COLUMN_NAME_AMOUNT)),
                    time = getString(getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.COLUMN_NAME_TIME)),
                    comment = getString(getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.COLUMN_NAME_COMMENT))
                )

                expenseList.add(expense)
            }
        }
        cursor.close()
        return expenseList
    }


    fun removeAll() {
        writableDatabase.delete(TABLE_NAME, null, null)
    }

    fun removeAll(tripId: Long) {

        val selection = "${ExpenseContract.ExpenseEntry.COLUMN_NAME_TRIP_ID} = ?"
        val selectionArgs = arrayOf("$tripId")

        writableDatabase.delete(TABLE_NAME, selection, selectionArgs)
    }

    fun removeExpense(expenseId: Long) {
        writableDatabase.delete(
            TABLE_NAME,
            "${BaseColumns._ID} = ?",
            arrayOf(expenseId.toString())
        )
    }

    fun getExpenseById(expenseId: Long): Expense? {
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("$expenseId")

        val cursor = readableDatabase.query(
            TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            "${BaseColumns._ID} DESC"
        )

        var expense: Expense? = null

        with(cursor) {
            while (moveToNext()) {
                expense = Expense(
                    id = getLong(getColumnIndexOrThrow(BaseColumns._ID)),
                    tripId = getLong(getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.COLUMN_NAME_TRIP_ID)),
                    type = getString(getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.COLUMN_NAME_TYPE)),
                    amount = getString(getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.COLUMN_NAME_AMOUNT)),
                    time = getString(getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.COLUMN_NAME_TIME)),
                    comment = getString(getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.COLUMN_NAME_COMMENT))
                )
            }
        }
        cursor.close()

        return expense
    }

    fun update(expenseId: Long, expense: Expense) {
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("$expenseId")

        val count = writableDatabase.update(
            TABLE_NAME,
            expense.getContentValues(),
            selection,
            selectionArgs
        )
    }

}