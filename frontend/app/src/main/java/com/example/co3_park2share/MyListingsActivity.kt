package com.example.co3_park2share

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.co3_park2share.viewmodel.MyListingsViewModel
import com.example.co3_park2share.viewmodel.UserViewModel


class MyListingsActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModels()
    private val myListingsViewModel: MyListingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        userViewModel.loggedInUser.observe(this) { user ->
            if (user == null) {
                Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                myListingsViewModel.fetchListings(user.uid, this)
                setContent {
                    MaterialTheme {
                        BaseDrawerScreen(title = "My Listings") { paddingValues ->
                            MyListingsContent(
                                paddingValues = paddingValues,
                                listingsViewModel = myListingsViewModel,
                            )
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun MyListingsContent(
    paddingValues: PaddingValues,
    listingsViewModel: MyListingsViewModel
) {
    val listings by remember { derivedStateOf { listingsViewModel.listings } }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text("My Listings", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(listings) { listing ->
                ListingItem(
                    listing = listing,
                    onDelete = { listingsViewModel.deleteListing(listing.cid, context) },
                    navigateToUpdate = { carId ->
                        val intent = Intent(context, UpdateCarActivity::class.java)
                        intent.putExtra("CAR_ID", carId)
                        context.startActivity(intent)
                    }
                )
                Divider()
            }
        }
    }
}



@Composable
fun ListingItem(
    listing: Listing,
    onDelete: () -> Unit,
    navigateToUpdate: (Int) -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("${listing.brand} - ${listing.model}", style = MaterialTheme.typography.h6)
            Text("Plate: ${listing.plate}")
            Text("Capacity: ${listing.capacity}")
            Text("Location: ${listing.location}")
            Text("Price: \$${listing.price}")
            Text("Available: ${if (listing.isAvailable) "Yes" else "No"}")

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    onDelete()
                    // Restart the activity to refresh the list
                    val intent = Intent(context, MyListingsActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)
                }) {
                    Text("Delete")
                }

                Button(
                    onClick = { navigateToUpdate(listing.cid) },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary)
                ) {
                    Text("Update")
                }
            }
        }
    }
}
