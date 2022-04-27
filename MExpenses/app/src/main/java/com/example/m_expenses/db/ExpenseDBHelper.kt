package com.example.m_expenses.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ExpenseDBHelper(
    context: Context
) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(TripContract.CREATE_TRIPS_TABLE)
        db.execSQL(ExpenseContract.CREATE_EXPENSES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL(TripContract.SQL_DELETE_ENTRIES)
        db.execSQL(ExpenseContract.SQL_DELETE_ENTRIES)
        onCreate(db)
    }


    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 2
        const val DATABASE_NAME = "Expense.db"
    }
}