package com.example.m_expenses.features.tripdetails

interface OnExpenseClickListener {

    fun onDeleteExpenseClicked(expenseId: Long)
    fun onEditExpenseClicked(expenseId: Long)
}