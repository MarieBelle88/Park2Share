package com.example.co3_park2share

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MyListingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                BaseDrawerScreen(title = "My Listings") { paddingValues ->
                    MyListingsContent(paddingValues)
                }
            }
        }
    }

}

@Composable
fun MyListingsContent(paddingValues: PaddingValues) {
    // Dummy data
    val bookingsList = listOf(
        Listing("aaa", "aaaa", "aaa", "aaa", 5,"aaa",50.0f,true),
        Listing("aaa", "aaaa", "aaa", "aaa", 3,"aaa",20.0f,false)    )

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text("My Listings", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))

       /* LazyColumn {
            items(bookingsList) { listing ->
                BookingItem(Listing)
                Divider()
            }
        }*/
    }
}