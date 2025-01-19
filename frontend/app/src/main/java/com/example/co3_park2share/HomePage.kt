package com.example.co3_park2share

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext


@Composable
fun DashboardScreen() {
    Surface(color = MaterialTheme.colors.background) {
        Column(modifier = Modifier.padding(16.dp)) {
            QuickLinksSection()
            Spacer(modifier = Modifier.height(16.dp))
            NotificationsSection()
            Spacer(modifier = Modifier.height(16.dp))
            SuggestedCarsSection()
            Spacer(modifier = Modifier.height(16.dp))
            MyActivitySection()
        }
    }
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
            Image(
                painter = painterResource(id = icon),
                contentDescription = title,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold)
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
            Text("Notifications", fontSize = 18.sp, fontWeight = FontWeight.Bold)
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
        Text("Suggested Cars", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SuggestedCarCard(carName = "Tesla Model 3", image = R.drawable.car)
            SuggestedCarCard(carName = "Ford Mustang", image = R.drawable.car)
            SuggestedCarCard(carName = "Toyota Prius", image = R.drawable.car)
        }
    }
}

@Composable
fun SuggestedCarCard(carName: String, image: Int) {
    val context = LocalContext.current // Get the current context to create an Intent

    Card(
        modifier = Modifier
            .width(100.dp)
            .height(120.dp)
            .clickable {
                // Navigate to CarDetailsActivity with the car details
                val intent = Intent(context, CarDetailsActivity::class.java).apply {
                    putExtra("carName", carName)
                    putExtra("carImage", image)
                    putExtra("carCapacity", "4 people") // Example capacity
                    putExtra("carPrice", "$50/day") // Example price
                }
                context.startActivity(intent)
            },
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = carName,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = carName, fontSize = 14.sp, fontWeight = FontWeight.Bold)
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
            Text("My Activity", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text("- Upcoming Trip: Jan 22, 2025")
            Text("- Listed Cars: 3 Active")
            Text("- Pending Actions: 2")
        }
    }
}
