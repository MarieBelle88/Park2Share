package com.example.co3_park2share

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun BaseDrawerScreen(
    title: String,
    content: @Composable (PaddingValues) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent { selectedOption ->
                when (selectedOption) {
                    "Home" -> navigateToActivity(context, HomePageActivity::class.java)
                    "List Your Car" -> navigateToActivity(context, ListYourCarActivity::class.java)
                    "My Bookings" -> navigateToActivity(context, MyBookingsActivity::class.java)
                    "My Listings" -> navigateToActivity(context, MyListingsActivity::class.java)
                    "Settings" -> navigateToActivity(context, SettingsActivity::class.java)
                }
                scope.launch { drawerState.close() }
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
                    },
                    modifier = Modifier.statusBarsPadding()
                )
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    content(paddingValues)
                }
            }
        )
    }
}

fun navigateToActivity(context: Context, activityClass: Class<*>) {
    val intent = Intent(context, activityClass)
    context.startActivity(intent)
}
