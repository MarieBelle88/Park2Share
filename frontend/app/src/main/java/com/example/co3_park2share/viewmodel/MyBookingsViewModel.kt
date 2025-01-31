package com.example.co3_park2share.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.co3_park2share.Booking
import com.example.co3_park2share.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyBookingsViewModel : ViewModel() {
    private val _bookings = MutableStateFlow<List<Booking>>(emptyList())
    val bookings: StateFlow<List<Booking>> get() = _bookings.asStateFlow()

    fun fetchBookings(userId: Int, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.apiService.getUserBookings(userId)

                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        _bookings.emit(data)
                    } ?: showToast(context, "No bookings found.")
                } else {
                    showToast(context, "API Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                showToast(context, "Network error: ${e.message}")
            }
        }
    }

    fun deleteBooking(bookingId: Int, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.apiService.deleteBooking(bookingId)

                if (response.isSuccessful) {
                    _bookings.emit(_bookings.value.filter { it.bid != bookingId })
                    showToast(context, "Booking deleted successfully")
                } else {
                    showToast(context, "Failed to delete booking: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                showToast(context, "Network error: ${e.message}")
            }
        }
    }

    private fun showToast(context: Context, message: String) {
        viewModelScope.launch(Dispatchers.Main) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}