package com.example.m_expenses.models

import android.content.ContentValues
import android.provider.BaseColumns
import com.example.m_expenses.db.TripContract

data class Trip(
    val id: Long? = null,
    val name: String,
    val destination: String,
    val date: String,
    val requireRisk: Boolean,
    val description: String = ""
) {


    fun getContentValues(): ContentValues {
        return ContentValues().apply {
            if (id != null) put(BaseColumns._ID, id)
            put(TripContract.TripEntry.COLUMN_NAME_NAME, name)
            put(TripContract.TripEntry.COLUMN_NAME_DESTINATION, destination)
            put(TripContract.TripEntry.COLUMN_NAME_DATE, date)
            put(TripContract.TripEntry.COLUMN_NAME_REQUIRE_RISK, if (requireRisk) 1 else 0)
            put(TripContract.TripEntry.COLUMN_NAME_DESCRIPTION, description)
        }
    }
}