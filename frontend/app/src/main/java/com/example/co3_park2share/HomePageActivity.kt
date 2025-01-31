package com.example.co3_park2share

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.io.OutputStreamWriter
import com.example.co3_park2share.viewmodel.UserViewModel

class HomePageActivity : ComponentActivity() {
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                BaseDrawerScreen(title = "Dashboard") { paddingValues ->
                    DashboardContent(paddingValues, userViewModel)
                }
            }
        }
    }
}

@Composable
fun DashboardContent(paddingValues: PaddingValues, userViewModel: UserViewModel) {
    var searchText by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val cars = remember { mutableStateListOf<Map<String, String>>() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        isLoading = true
        try {
            val fetchedCars = fetchCarsFromApi()
            cars.clear()
            cars.addAll(fetchedCars)
        } catch (e: Exception) {
            Toast.makeText(context, "Failed to load cars: ${e.message}", Toast.LENGTH_SHORT).show()
        } finally {
            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val painter = painterResource(id = R.drawable.icon)
        Image(
            painter = painter,
            contentDescription = "App Logo",
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))


        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Search for cars...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))


        if (isLoading) {
            CircularProgressIndicator()
        } else if (cars.isEmpty()) {
            Text("No cars available", modifier = Modifier.padding(16.dp))
        } else {
            Text("Car Listings", fontSize = 20.sp, style = MaterialTheme.typography.h6)
            CarListingsSection(cars, searchText, coroutineScope, userViewModel, context) { cid ->

                cars.removeAll { it["cid"] == cid.toString() }
            }
        }
    }
}

@Composable
fun CarListingsSection(
    cars: List<Map<String, String>>,
    searchText: String,
    coroutineScope: CoroutineScope,
    userViewModel: UserViewModel,
    context: android.content.Context,
    onCarBooked: (Int) -> Unit
) {
    val filteredCars = cars.filter {
        it["brand"]?.contains(searchText, ignoreCase = true) == true ||
                it["model"]?.contains(searchText, ignoreCase = true) == true ||
                it["location"]?.contains(searchText, ignoreCase = true) == true
    }

    if (filteredCars.isEmpty()) {
        Text("No cars found matching your search.", modifier = Modifier.padding(16.dp))
    } else {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(filteredCars) { car ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = 4.dp
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "${car["brand"]} ${car["model"]}",
                            fontSize = 18.sp,
                            style = MaterialTheme.typography.subtitle1
                        )
                        Text("${car["color"]}, ${car["capacity"]} seats", fontSize = 14.sp)
                        Text("Location: ${car["location"]}", fontSize = 14.sp)
                        Text("Price: $${car["price"]}/day", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                car["cid"]?.toIntOrNull()?.let { carId ->
                                    val uid = userViewModel.loggedInUser.value?.uid
                                    if (uid != null) {
                                        coroutineScope.launch {
                                            try {
                                                bookCar(carId, uid)
                                                Toast.makeText(context, "Car booked successfully!", Toast.LENGTH_SHORT).show()
                                                onCarBooked(carId)
                                            } catch (e: Exception) {
                                                Toast.makeText(context, "Failed to book car: ${e.message}", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    } else {
                                        Toast.makeText(context, "User not logged in", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Book This Car")
                        }
                    }
                }
            }
        }
    }
}

suspend fun fetchCarsFromApi(): List<Map<String, String>> {
    return withContext(Dispatchers.IO) {
        try {
            val url = URL("http://10.0.2.2:8080/cars")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                val jsonArray = JSONArray(response)

                (0 until jsonArray.length()).map { i ->
                    val jsonObject = jsonArray.getJSONObject(i)
                    mapOf(
                        "cid" to jsonObject.getString("cid"),
                        "brand" to jsonObject.getString("brand"),
                        "model" to jsonObject.getString("model"),
                        "color" to jsonObject.getString("color"),
                        "capacity" to jsonObject.getString("capacity"),
                        "location" to jsonObject.getString("location"),
                        "price" to jsonObject.getString("price"),
                        "isAvailable" to jsonObject.getString("isAvailable")
                    )
                }.filter { it["isAvailable"] == "true" }
            } else {
                throw Exception("Failed to fetch cars: HTTP ${connection.responseCode}")
            }
        } catch (e: Exception) {
            throw Exception("Network error: ${e.message}")
        }
    }
}

suspend fun bookCar(cid: Int, uid: Int) {
    withContext(Dispatchers.IO) {
        try {
            val url = URL("http://10.0.2.2:8080/bookings")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.doOutput = true
            connection.setRequestProperty("Content-Type", "application/json")

            val jsonObject = JSONObject().apply {
                put("cid", cid)
                put("uid", uid)
            }

            OutputStreamWriter(connection.outputStream).use { it.write(jsonObject.toString()) }

            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                throw Exception("Failed to book car: HTTP ${connection.responseCode}")
            }
        } catch (e: Exception) {
            throw Exception("Network error: ${e.message}")
        }
    }
}