package com.example.co3_park2share

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.DataInput


class ListYourCarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_your_car)

        val brandInput: EditText = findViewById(R.id.brandInput)
        val modelInput: EditText = findViewById(R.id.modelInput)
        val colorInput: EditText = findViewById(R.id.colorInput)
        val plateInput: EditText = findViewById(R.id.plateInput)
        val capacityInput : EditText = findViewById(R.id.capacityInput)
        val locationInput: EditText = findViewById(R.id.locationInput)
        val priceInput: EditText = findViewById(R.id.priceInput)
        val availabilitySwitch: Switch = findViewById(R.id.availabilitySwitch)
        val addCarButton: Button = findViewById(R.id.addCarButton)

        addCarButton.setOnClickListener {
            val brand = brandInput.text.toString()
            val model = modelInput.text.toString()
            val color = colorInput.text.toString()
            val plate = plateInput.text.toString()
            val capacity = capacityInput.text.toString()
            val location = locationInput.text.toString()
            val price = priceInput.text.toString().toFloatOrNull()
            val isAvailable = availabilitySwitch.isChecked

            if (brand.isEmpty() || model.isEmpty() || color.isEmpty() || plate.isEmpty() || location.isEmpty() || price == null) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Send the data to the backend
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val car = mapOf(
                        "brand" to brand,
                        "model" to model,
                        "color" to color,
                        "plate" to plate,
                        "capacity" to capacity,
                        "location" to location,
                        "price" to price.toString(),
                        "isAvailable" to isAvailable.toString()
                    )
             /*       ApiClient.service.addCar(car) // Assuming an addCar() function exists in ApiService
                    runOnUiThread {
                        Toast.makeText(this@ListYourCarActivity, "Car added successfully", Toast.LENGTH_SHORT).show()
                        clearFields(brandInput, modelInput, colorInput, plateInput, locationInput, priceInput, availabilitySwitch)
                    }*/
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(this@ListYourCarActivity, "Error adding car", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun clearFields(
        brandInput: EditText,
        modelInput: EditText,
        colorInput: EditText,
        plateInput: EditText,
        capacityInput: EditText,
        locationInput: EditText,
        priceInput: EditText,
        availabilitySwitch: Switch
    ) {
        brandInput.text.clear()
        modelInput.text.clear()
        colorInput.text.clear()
        plateInput.text.clear()
        capacityInput.text.clear()
        locationInput.text.clear()
        priceInput.text.clear()
        availabilitySwitch.isChecked = false
    }
}
