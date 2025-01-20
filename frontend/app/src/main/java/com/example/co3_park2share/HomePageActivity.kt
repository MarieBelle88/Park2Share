package com.example.co3_park2share

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.clickable
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu




class HomePageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                DashboardWithDrawer()
            }
        }
    }
}

@Composable
fun DashboardWithDrawer() {
    val drawerState = rememberDrawerState(DrawerValue.Closed) // Create the DrawerState
    val scope = rememberCoroutineScope() // Coroutine scope for toggling the drawer

    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent { selectedOption ->
                // Handle option selection logic
                when (selectedOption) {
                    "Home" -> { /* Navigate to Home */ }
                    "List Your Car" -> { /* Navigate to List Your Car */ }
                    "My Bookings" -> { /* Navigate to My Bookings */ }
                    "Settings" -> { /* Navigate to Settings */ }
                }
                scope.launch { drawerState.close() } // Close the drawer after selection
            }
        }
    ) {
        // Pass the drawerState to DashboardScreen
        DashboardScreen(drawerState = drawerState)
    }
}


@Composable
fun DashboardScreen(drawerState: DrawerState) {
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard") },
                navigationIcon = {
                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        },
        content = { paddingValues -> // Accept the padding values
            Column(
                modifier = Modifier
                    .padding(paddingValues) // Use padding values from Scaffold
                    .padding(16.dp) // Add custom padding
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
    )
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
