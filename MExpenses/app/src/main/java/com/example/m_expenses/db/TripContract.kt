package com.example.m_expenses.db

import android.provider.BaseColumns

object TripContract {

    const val CREATE_TRIPS_TABLE = "CREATE TABLE ${TripEntry.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${TripEntry.COLUMN_NAME_NAME} TEXT," +
            "${TripEntry.COLUMN_NAME_DESTINATION} TEXT," +
            "${TripEntry.COLUMN_NAME_DATE} TEXT," + //timestamp
            "${TripEntry.COLUMN_NAME_REQUIRE_RISK} INTEGER," + //0(NO) or 1(YES)
            "${TripEntry.COLUMN_NAME_DESCRIPTION} TEXT)"

     const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${TripContract.TripEntry.TABLE_NAME}"

    object TripEntry : BaseColumns {
        const val TABLE_NAME = "trips"

        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_DESTINATION = "destination"
        const val COLUMN_NAME_DATE = "date"
        const val COLUMN_NAME_REQUIRE_RISK = "requireRisk"
        const val COLUMN_NAME_DESCRIPTION = "description"
    }

}