package com.example.co3_park2share

import androidx.compose.material3.*
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import androidx.activity.viewModels
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.co3_park2share.viewmodel.UserViewModel

class ListYourCarActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()


        // Observe the logged-in user
        userViewModel.loggedInUser.observe(this) { user ->
            if (user == null) {
                // User is not logged in
                Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                // Use the logged-in user's ID in your API requests
                setContent {
                    MaterialTheme {
                        BaseDrawerScreen(title = "List Your Car") { paddingValues ->
                            ListYourCarContent(
                                paddingValues = paddingValues,
                                loggedInUserId = user.uid
                            )
                        }
                    }
                }
            }
        }
    }
}




@Composable
fun ListYourCarContent(
    paddingValues: PaddingValues,
    loggedInUserId: Int // Add loggedInUserId as a parameter
) {
    var brand by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var plate by remember { mutableStateOf("") }
    var capacity by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var isAvailable by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = brand,
            onValueChange = { brand = it },
            label = { Text("Brand") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = model,
            onValueChange = { model = it },
            label = { Text("Model") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = color,
            onValueChange = { color = it },
            label = { Text("Color") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = plate,
            onValueChange = { plate = it },
            label = { Text("Plate") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = capacity,
            onValueChange = { capacity = it },
            label = { Text("Capacity") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Available")
            Spacer(modifier = Modifier.width(8.dp))
            Switch(checked = isAvailable, onCheckedChange = { isAvailable = it })
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Add Car Button
        Button(
            onClick = {
                val missingFields = mutableListOf<String>()

                if (brand.trim().isEmpty()) missingFields.add("Brand")
                if (model.trim().isEmpty()) missingFields.add("Model")
                if (color.trim().isEmpty()) missingFields.add("Color")
                if (plate.trim().isEmpty()) missingFields.add("Plate")
                if (capacity.trim().isEmpty()) missingFields.add("Capacity")
                if (location.trim().isEmpty()) missingFields.add("Location")
                if (price.trim().isEmpty()) missingFields.add("Price")

                if (missingFields.isNotEmpty()) {
                    val errorMessage = "Missing fields: ${missingFields.joinToString(", ")}"
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                } else {

                    val car = mapOf(
                        "uid" to loggedInUserId.toString(), // Use the logged-in user's ID
                        "brand" to brand,
                        "model" to model,
                        "color" to color,
                        "plate" to plate,
                        "capacity" to capacity,
                        "location" to location,
                        "price" to price,
                        "isAvailable" to isAvailable.toString()
                    )

                    // Make the API call
                    scope.launch {
                        try {
                            val response: Response<Map<String, String>> = withContext(Dispatchers.IO) {
                                RetrofitClient.apiService.addCar(car)
                            }

                            withContext(Dispatchers.Main) {
                                if (response.isSuccessful) {
                                    val responseBody = response.body()
                                    if (responseBody != null && responseBody["message"] == "Car added successfully") {
                                        Toast.makeText(context, "Car added successfully", Toast.LENGTH_SHORT).show()

                                        // Clear the fields
                                        brand = ""
                                        model = ""
                                        color = ""
                                        plate = ""
                                        capacity = ""
                                        location = ""
                                        price = ""
                                        isAvailable = false
                                    } else {
                                        Toast.makeText(context, "Unexpected response: $responseBody", Toast.LENGTH_SHORT).show()
                                    }
                                } else {
                                    val errorBody = response.errorBody()?.string()
                                    Toast.makeText(context, "Error adding car: $errorBody", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Car")
        }
    }
}