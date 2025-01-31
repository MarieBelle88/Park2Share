package com.example.co3_park2share.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.co3_park2share.Listing
import com.example.co3_park2share.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import androidx.compose.runtime.mutableStateListOf
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity


class MyListingsViewModel : ViewModel() {

    private val _listings = mutableStateListOf<Listing>()
    val listings: List<Listing> get() = _listings

    fun fetchListings(userId: Int, context: Context) {
        viewModelScope.launch {
            try {
                val response: Response<List<Listing>> = withContext(Dispatchers.IO) {
                    RetrofitClient.apiService.getUserListings(userId)
                }

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            _listings.clear()
                            _listings.addAll(body)
                        } else {
                            Toast.makeText(context, "Empty response from server", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Toast.makeText(context, "API Error: $errorBody", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Network error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun deleteListing(cid: Int, context: Context) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.apiService.deleteCar(cid)
                }
                if (response.isSuccessful) {
                    _listings.removeAll { it.cid == cid }
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Listing deleted successfully", Toast.LENGTH_SHORT).show()
                        (context as? AppCompatActivity)?.recreate()
                    }
                } else {
                    Toast.makeText(context, "Failed to delete listing: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }


}
