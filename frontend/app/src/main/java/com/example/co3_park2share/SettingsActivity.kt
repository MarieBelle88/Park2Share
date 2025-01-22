package com.example.co3_park2share

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import android.content.Context.MODE_PRIVATE
import android.widget.Toast




class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                BaseDrawerScreen(title = "Settings") { paddingValues ->
                    SettingsContent(paddingValues)
                }
            }
        }
    }
}

@Composable
fun SettingsContent(paddingValues: PaddingValues) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("Settings", MODE_PRIVATE)

    var notificationsEnabled by remember { mutableStateOf(sharedPreferences.getBoolean("notifications", false)) }
    var darkModeEnabled by remember { mutableStateOf(sharedPreferences.getBoolean("darkMode", false)) }
    var selectedLanguage by remember { mutableStateOf(sharedPreferences.getString("language", "English") ?: "English") }

    val languages = listOf("English", "French", "Spanish", "German")

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text("Settings", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(16.dp))

        // Notifications Switch
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enable Notifications", modifier = Modifier.weight(1f))
            Switch(checked = notificationsEnabled, onCheckedChange = { notificationsEnabled = it })
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Dark Mode Switch
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enable Dark Mode", modifier = Modifier.weight(1f))
            Switch(checked = darkModeEnabled, onCheckedChange = { darkModeEnabled = it })
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Language Selection
        Text("Select Language")
        Column {
            languages.forEach { language ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    RadioButton(
                        selected = selectedLanguage == language,
                        onClick = { selectedLanguage = language }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(language)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Save Button
        Button(
            onClick = {
                // Save settings
                sharedPreferences.edit()
                    .putBoolean("notifications", notificationsEnabled)
                    .putBoolean("darkMode", darkModeEnabled)
                    .putString("language", selectedLanguage)
                    .apply()

                // Show confirmation
                Toast.makeText(context, "Settings saved!", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Settings")
        }
    }
}

