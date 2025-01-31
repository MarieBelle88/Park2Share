package com.example.co3_park2share

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.co3_park2share.viewmodel.SettingsViewModel
import com.example.co3_park2share.viewmodel.UserViewModel
import androidx.compose.runtime.livedata.observeAsState

class SettingsActivity : ComponentActivity() {
    private val settingsViewModel: SettingsViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                BaseDrawerScreen(title = "Settings") { paddingValues ->
                    SettingsContent(paddingValues, settingsViewModel, userViewModel)
                }
            }
        }
    }
}

@Composable
fun SettingsContent(
    paddingValues: PaddingValues,
    settingsViewModel: SettingsViewModel,
    userViewModel: UserViewModel
) {
    val context = LocalContext.current
    val loggedInUser by userViewModel.loggedInUser.observeAsState(initial = null)  // ✅ Fix applied

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Settings", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                settingsViewModel.logout(context)
                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log Out")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                loggedInUser?.uid?.let { userId ->
                    settingsViewModel.deleteAccount(userId, context) {
                        val intent = Intent(context, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        context.startActivity(intent)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
        ) {
            Text("Delete Account")
        }
    }
}
