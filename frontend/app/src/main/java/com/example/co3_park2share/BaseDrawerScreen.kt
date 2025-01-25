package com.example.co3_park2share

import android.content.Context
import android.content.Intent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.platform.LocalContext

@Composable
fun BaseDrawerScreen(
    title: String,
    content: @Composable (PaddingValues) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current // Access the current context for navigation

    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent { selectedOption ->
                // Handle navigation logic here
                when (selectedOption) {
                    "Home" -> navigateToActivity(context, HomePageActivity::class.java)
                    "List Your Car" -> navigateToActivity(context, ListYourCarActivity::class.java)
                    "My Bookings" -> navigateToActivity(context, MyBookingsActivity::class.java)
                    "My Listings" -> navigateToActivity(context, MyListingsActivity::class.java)
                    "My Account" -> navigateToActivity(context, MyAccountActivity::class.java)
                    "Settings" -> navigateToActivity(context, SettingsActivity::class.java)

                }
                scope.launch { drawerState.close() } // Close the drawer after selection
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(title) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },
            content = { paddingValues ->
                content(paddingValues) // Inject specific page content here
            }
        )
    }
}

// Helper function to navigate between activities
fun navigateToActivity(context: Context, activityClass: Class<*>) {
    val intent = Intent(context, activityClass)
    context.startActivity(intent)
}
