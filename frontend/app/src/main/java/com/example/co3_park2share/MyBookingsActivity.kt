package com.example.co3_park2share

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MyBookingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                BaseDrawerScreen(title = "My Bookings") { paddingValues ->
                    MyBookingsContent(paddingValues)
                }
            }
        }
    }
}


@Composable
fun MyBookingsContent(paddingValues: PaddingValues) {
    // Dummy data
    val bookingsList = listOf(
        Booking("Car A", "Jan 20, 2025", "Jan 27, 2025"),
        Booking("Car B", "Feb 1, 2025", "Feb 10, 2025")
    )

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text("My Bookings", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(bookingsList) { booking ->
                BookingItem(booking)
                Divider()
            }
        }
    }
}

@Composable
fun BookingItem(booking: Booking) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text("Car: ${booking.carName}", style = MaterialTheme.typography.body1)
        Text("Start Date: ${booking.startDate}", style = MaterialTheme.typography.body2)
        Text("End Date: ${booking.endDate}", style = MaterialTheme.typography.body2)
    }
}
