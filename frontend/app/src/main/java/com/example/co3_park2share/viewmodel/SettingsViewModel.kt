package com.example.co3_park2share.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.co3_park2share.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel : ViewModel() {

    fun logout(context: Context) {
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
        Toast.makeText(context, "Logged out successfully!", Toast.LENGTH_SHORT).show()
    }

    fun deleteAccount(userId: Int, context: Context, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.apiService.deleteUser(userId)
                }

                if (response.isSuccessful) {
                    logout(context)
                    onSuccess()
                } else {
                    Log.e("SettingsViewModel", "Failed to delete account: ${response.errorBody()?.string()}")
                    Toast.makeText(context, "Failed to delete account", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("SettingsViewModel", "Error deleting account: ${e.message}", e)
                Toast.makeText(context, "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
