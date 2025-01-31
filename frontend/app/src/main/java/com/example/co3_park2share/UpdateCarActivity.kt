package com.example.co3_park2share

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.co3_park2share.viewmodel.MyListingsViewModel
import kotlinx.coroutines.launch
import retrofit2.Response

class UpdateCarActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val carId = intent.getIntExtra("CAR_ID", -1) // ✅ Get CID from intent
        if (carId == -1) {
            Toast.makeText(this, "Error: Invalid car ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setContent {
            MaterialTheme {
                UpdateCarContent(carId, this)
            }
        }
    }
}
@Composable
fun UpdateCarContent(carId: Int, activity: ComponentActivity) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var car by remember { mutableStateOf<Listing?>(null) }

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val response = RetrofitClient.apiService.getCarDetails(carId)
                if (response.isSuccessful) {
                    car = response.body()
                } else {
                    Toast.makeText(context, "Failed to load car details", Toast.LENGTH_SHORT).show()
                    activity.finish()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
                activity.finish()
            }
        }
    }

    if (car == null) {
        Text("Loading car details...", modifier = Modifier.padding(16.dp))
        return
    }

    var brand by remember { mutableStateOf(car!!.brand) }
    var model by remember { mutableStateOf(car!!.model) }
    var color by remember { mutableStateOf(car!!.color) }
    var plate by remember { mutableStateOf(car!!.plate) }
    var capacity by remember { mutableStateOf(car!!.capacity.toString()) }
    var location by remember { mutableStateOf(car!!.location) }
    var price by remember { mutableStateOf(car!!.price.toString()) }
    var isAvailable by remember { mutableStateOf(car!!.isAvailable) }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(value = brand, onValueChange = { brand = it }, label = { Text("Brand") })
        OutlinedTextField(value = model, onValueChange = { model = it }, label = { Text("Model") })
        OutlinedTextField(value = color, onValueChange = { color = it }, label = { Text("Color") })
        OutlinedTextField(value = plate, onValueChange = { plate = it }, label = { Text("Plate") })
        OutlinedTextField(value = capacity, onValueChange = { capacity = it }, label = { Text("Capacity") })
        OutlinedTextField(value = location, onValueChange = { location = it }, label = { Text("Location") })
        OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("Price") })

        Row(modifier = Modifier.fillMaxWidth()) {
            Text("Available")
            Switch(checked = isAvailable, onCheckedChange = { isAvailable = it })
        }

        Button(
            onClick = {
                val updatedCar = mapOf(
                    "uid" to car!!.uid.toString(),
                    "brand" to brand,
                    "model" to model,
                    "color" to color,
                    "plate" to plate,
                    "capacity" to capacity,
                    "location" to location,
                    "price" to price,
                    "isAvailable" to isAvailable.toString()
                )

                scope.launch {
                    try {
                        val response: Response<String> = RetrofitClient.apiService.updateCar(carId, updatedCar)
                        if (response.isSuccessful) {
                            Toast.makeText(context, "Car updated successfully", Toast.LENGTH_SHORT).show()

                            val intent = Intent(context, MyListingsActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            context.startActivity(intent)
                            activity.finish()
                        } else {
                            val errorBody = response.errorBody()?.string()
                            Toast.makeText(context, "Failed to update car: $errorBody", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(context, "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update Car")
        }
    }
}