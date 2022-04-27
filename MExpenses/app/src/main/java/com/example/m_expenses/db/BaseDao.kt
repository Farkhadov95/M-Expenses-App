package com.example.m_expenses.db

import android.content.Context

abstract class BaseDao(context: Context) {

    private val dbHelper = ExpenseDBHelper(context)
    protected val writableDatabase = dbHelper.writableDatabase
    protected val readableDatabase = dbHelper.readableDatabase

    open fun clear(){
        dbHelper.close()
    }
}