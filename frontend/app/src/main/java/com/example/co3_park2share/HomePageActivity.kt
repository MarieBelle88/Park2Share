package com.example.co3_park2share

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.*
import androidx.compose.ui.Alignment



class HomePageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                BaseDrawerScreen(title = "Dashboard") { paddingValues ->
                    DashboardContent(paddingValues)
                }
            }
        }
    }
}

@Composable
fun DashboardContent(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        QuickLinksSection() // Reused from DrawerComponents
        Spacer(modifier = Modifier.height(16.dp))
        NotificationsSection()
        Spacer(modifier = Modifier.height(16.dp))
        SuggestedCarsSection()
        Spacer(modifier = Modifier.height(16.dp))
        MyActivitySection()
    }
}

@Composable
fun NotificationsSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Notifications", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(8.dp))
            Text("- Upcoming Booking: Jan 20, 2025")
            Text("- Pending Photo Submission")
            Text("- New Messages")
        }
    }
}

@Composable
fun SuggestedCarsSection() {
    Column {
        Text("Suggested Cars", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SuggestedCarCard(carName = "Tesla Model 3")
            SuggestedCarCard(carName = "Ford Mustang")
            SuggestedCarCard(carName = "Toyota Prius")
        }
    }
}

@Composable
fun SuggestedCarCard(carName: String) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(120.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = carName, style = MaterialTheme.typography.body1)
        }
    }
}

@Composable
fun MyActivitySection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("My Activity", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(8.dp))
            Text("- Upcoming Trip: Jan 22, 2025")
            Text("- Listed Cars: 3 Active")
            Text("- Pending Actions: 2")
        }
    }
}
