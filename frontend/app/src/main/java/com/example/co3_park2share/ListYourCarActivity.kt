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



class ListYourCarActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                BaseDrawerScreen(title = "List Your Car") { paddingValues ->
                    ListYourCarContent(paddingValues)
                }
            }
        }
    }
}

@Composable
fun ListYourCarContent(paddingValues: PaddingValues) {
    var brand by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var plate by remember { mutableStateOf("") }
    var capacity by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var isAvailable by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

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
        val context = LocalContext.current // Declare this once outside the Button

        Button(
            onClick = {
                if (brand.isEmpty() || model.isEmpty() || color.isEmpty() || plate.isEmpty() || location.isEmpty() || price.isEmpty()) {
                    Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                } else {
                    // Send the data to the backend
                    scope.launch {
                        try {
                            val car = mapOf(
                                "brand" to brand,
                                "model" to model,
                                "color" to color,
                                "plate" to plate,
                                "capacity" to capacity,
                                "location" to location,
                                "price" to price,
                                "isAvailable" to isAvailable.toString()
                            )
                            // Replace this with your actual API call
                            Toast.makeText(context, "Car added successfully", Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            Toast.makeText(context, "Error adding car", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Car")
        }

        /*       ApiClient.service.addCar(car) // Assuming an addCar() function exists in ApiService
runOnUiThread {
    Toast.makeText(this@ListYourCarActivity, "Car added successfully", Toast.LENGTH_SHORT).show()
    clearFields(brandInput, modelInput, colorInput, plateInput, locationInput, priceInput, availabilitySwitch)
}*/

                            // Replace with API call


    }
}
