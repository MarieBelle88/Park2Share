package com.example.co3_park2share

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class MyBookingsActivity : AppCompatActivity() {



    private lateinit var bookingsRecyclerView: RecyclerView
    private lateinit var bookingsAdapter: BookingsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_bookings)

        // Initialize RecyclerView
        bookingsRecyclerView = findViewById(R.id.bookingsRecyclerView)
        bookingsRecyclerView.layoutManager = LinearLayoutManager(this)

        // Dummy data for demonstration
        val bookingsList = listOf(
            Booking("Car A", "Jan 20, 2025", "Jan 27, 2025"),
            Booking("Car B", "Feb 1, 2025", "Feb 10, 2025")
        )

        // Set up adapter
        bookingsAdapter = BookingsAdapter(bookingsList)
        bookingsRecyclerView.adapter = bookingsAdapter
    }
}
