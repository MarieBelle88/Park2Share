package com.example.co3_park2share

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.foundation.clickable
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
            .padding(paddingValues) // Padding from Scaffold
            .padding(16.dp) // Additional padding
    ) {
        QuickLinksSection()
        Spacer(modifier = Modifier.height(16.dp))
        NotificationsSection()
        Spacer(modifier = Modifier.height(16.dp))
        SuggestedCarsSection()
        Spacer(modifier = Modifier.height(16.dp))
        MyActivitySection()
    }
}

@Composable
fun DrawerContent(onOptionSelected: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Menu",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(16.dp)
        )
        Divider()
        DrawerItem(label = "Home", onClick = onOptionSelected)
        DrawerItem(label = "List Your Car", onClick = onOptionSelected)
        DrawerItem(label = "My Bookings", onClick = onOptionSelected)
        DrawerItem(label = "Settings", onClick = onOptionSelected)
    }
}

@Composable
fun DrawerItem(label: String, onClick: (String) -> Unit) {
    Text(
        text = label,
        style = MaterialTheme.typography.body1,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(label) }
            .padding(16.dp)
    )
}

@Composable
fun QuickLinksSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        QuickLinkCard(title = "List Your Car", icon = R.drawable.ic_launcher_foreground)
        QuickLinkCard(title = "Search for Cars", icon = R.drawable.ic_launcher_foreground)
        QuickLinkCard(title = "My Bookings", icon = R.drawable.ic_launcher_foreground)
    }
}

@Composable
fun QuickLinkCard(title: String, icon: Int) {
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
            // Example content for QuickLinkCard
            Text(text = title, style = MaterialTheme.typography.body1)
        }
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

