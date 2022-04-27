package com.example.m_expenses.features.triplist

interface OnTripClickListener {

    fun onTripClicked(tripId: Long)
    fun onDeleteTripClicked(tripId: Long)
    fun onEditTripClicked(tripId: Long)
}