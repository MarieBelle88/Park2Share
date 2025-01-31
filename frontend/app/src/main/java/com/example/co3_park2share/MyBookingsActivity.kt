package com.example.co3_park2share

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.co3_park2share.viewmodel.MyBookingsViewModel
import com.example.co3_park2share.viewmodel.UserViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.compose.runtime.livedata.observeAsState

class MyBookingsActivity : ComponentActivity() {
    private val userViewModel: UserViewModel by viewModels()
    private val myBookingsViewModel: MyBookingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                BaseDrawerScreen(title = "My Bookings") { paddingValues ->
                    MyBookingsContent(
                        paddingValues = paddingValues,
                        userViewModel = userViewModel,
                        myBookingsViewModel = myBookingsViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun MyBookingsContent(
    paddingValues: PaddingValues,
    userViewModel: UserViewModel,
    myBookingsViewModel: MyBookingsViewModel
) {
    val context = LocalContext.current
    val loggedInUser by userViewModel.loggedInUser.observeAsState()
    val bookings by myBookingsViewModel.bookings.collectAsState()

    LaunchedEffect(loggedInUser) {
        loggedInUser?.uid?.let { uid ->
            myBookingsViewModel.fetchBookings(uid, context)
        }
    }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text("My Bookings", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))

        if (bookings.isEmpty()) {
            Text("No bookings found.", modifier = Modifier.padding(16.dp))
        } else {
            LazyColumn {
                items(bookings) { booking ->
                    BookingItem(
                        booking = booking,
                        onDelete = {
                            myBookingsViewModel.deleteBooking(booking.bid, context)
                        }
                    )
                    Divider()
                }
            }
        }
    }
}


@Composable
fun BookingItem(
    booking: Booking,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("${booking.brand} ${booking.model}", style = MaterialTheme.typography.h6)
            Text("Color: ${booking.color}", style = MaterialTheme.typography.body1)
            Text("Plate: ${booking.plate}", style = MaterialTheme.typography.body1)
            Text("Capacity: ${booking.capacity} seats", style = MaterialTheme.typography.body1)
            Text("Location: ${booking.location}", style = MaterialTheme.typography.body1)
            Text("Price: $${booking.price}/day", style = MaterialTheme.typography.body1)

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    onDelete()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
            ) {
                Text("Delete Booking")
            }
        }
    }
}
