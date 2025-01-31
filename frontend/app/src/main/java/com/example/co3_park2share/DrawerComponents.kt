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

