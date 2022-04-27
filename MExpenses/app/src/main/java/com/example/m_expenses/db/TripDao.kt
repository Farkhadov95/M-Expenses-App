package com.example.m_expenses.db

import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import android.util.Log
import com.example.m_expenses.models.Trip

class TripDao private constructor(context: Context) : BaseDao(context) {

    companion object {
        private const val TAG = "TripDao"
        const val TABLE_NAME = TripContract.TripEntry.TABLE_NAME

        private var INSTANCE: TripDao? = null
        fun getInstance(context: Context): TripDao {
            if (INSTANCE == null) INSTANCE = TripDao(context)
            return INSTANCE!!
        }
    }

    override fun clear() {
        super.clear()
        INSTANCE = null
    }


    fun saveTrip(trip: Trip) {
        val count = writableDatabase.insert(
            TABLE_NAME,
            null,
            trip.getContentValues()
        )
        Log.i(TAG, "saveTrip: $count")
    }

    fun update(tripId: Long, trip: Trip) {

        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("$tripId")

        val count = writableDatabase.update(
            TABLE_NAME,
            trip.getContentValues(),
            selection,
            selectionArgs
        )
        Log.i(TAG, "saveTrip: $count")
    }

    fun remove(tripId: Long) {
        writableDatabase.delete(
            TABLE_NAME,
            "${BaseColumns._ID} = ?",
            arrayOf(tripId.toString())
        )
    }

    fun getTripById(tripId: Long): Trip? {

        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("$tripId")

        val cursor = readableDatabase.query(
            TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            "${TripContract.TripEntry.COLUMN_NAME_DATE} DESC"
        )
        var trip: Trip? = null
        with(cursor) {
            if (cursor.moveToNext())
                trip = Trip(
                    id = getLong(getColumnIndexOrThrow(BaseColumns._ID)),
                    name = getString(getColumnIndexOrThrow(TripContract.TripEntry.COLUMN_NAME_NAME)),
                    destination = getString(getColumnIndexOrThrow(TripContract.TripEntry.COLUMN_NAME_DESTINATION)),
                    date = getString(getColumnIndexOrThrow(TripContract.TripEntry.COLUMN_NAME_DATE)),
                    requireRisk = getInt(getColumnIndexOrThrow(TripContract.TripEntry.COLUMN_NAME_REQUIRE_RISK)) == 1,
                    description = getString(getColumnIndexOrThrow(TripContract.TripEntry.COLUMN_NAME_DESCRIPTION))
                )
        }
        cursor.close()
        return trip
    }


    fun getTrips(): List<Trip> {
        val cursor = readableDatabase.query(
            TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            "${BaseColumns._ID} DESC"
        )

        return mapCursor(cursor)
    }

    fun search(name: String = "", destination: String = "", date: String = ""): List<Trip> {
        //if (name.isEmpty() && destination.isEmpty() && date.isEmpty()) return getTrips()

        val query =
            "Select * FROM $TABLE_NAME where ${TripContract.TripEntry.COLUMN_NAME_NAME} LIKE '${name}%'" +
                    " AND ${TripContract.TripEntry.COLUMN_NAME_DESTINATION} LIKE '${destination}%'"
        if (date.isNotEmpty()) " AND ${TripContract.TripEntry.COLUMN_NAME_DATE} = $date"

        Log.i(TAG, "search query: $query")

        val cursor = readableDatabase.rawQuery(query, null)
        return mapCursor(cursor)
    }

    private fun mapCursor(cursor: Cursor): List<Trip> {
        val trips = mutableListOf<Trip>()
        with(cursor) {
            while (moveToNext()) {
                val trip = Trip(
                    id = getLong(getColumnIndexOrThrow(BaseColumns._ID)),
                    name = getString(getColumnIndexOrThrow(TripContract.TripEntry.COLUMN_NAME_NAME)),
                    destination = getString(getColumnIndexOrThrow(TripContract.TripEntry.COLUMN_NAME_DESTINATION)),
                    date = getString(getColumnIndexOrThrow(TripContract.TripEntry.COLUMN_NAME_DATE)),
                    requireRisk = getInt(getColumnIndexOrThrow(TripContract.TripEntry.COLUMN_NAME_REQUIRE_RISK)) == 1,
                    description = getString(getColumnIndexOrThrow(TripContract.TripEntry.COLUMN_NAME_DESCRIPTION))
                )
                trips.add(trip)
            }
        }
        cursor.close()
        return trips
    }

    fun removeAll() {
        writableDatabase.delete(TABLE_NAME, null, null)
    }
}