package com.example.co3_park2share

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
        DrawerItem(label = "My Listings", onClick = onOptionSelected)
        DrawerItem(label = "My Account", onClick = onOptionSelected)
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
            Text(text = title, style = MaterialTheme.typography.body1)
        }
    }
}
