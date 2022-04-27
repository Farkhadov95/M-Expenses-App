package com.example.m_expenses.features

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.m_expenses.R
import com.example.m_expenses.db.TripDao
import com.example.m_expenses.models.Trip


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}