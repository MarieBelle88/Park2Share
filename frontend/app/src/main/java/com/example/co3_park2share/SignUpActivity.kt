package com.example.co3_park2share

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.co3_park2share.databinding.ActivitySignUpBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        // Initialize View Binding
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listener on the Sign Up button
        binding.btnSignUpSubmit.setOnClickListener {
            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val email = binding.etSignUpEmail.text.toString()
            val password = binding.etSignUpPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()
            val phone = binding.etPhone.text.toString()

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                // Prepare the user data for the API request
                val userData = mapOf(
                    "first_name" to firstName,
                    "last_name" to lastName,
                    "email" to email,
                    "password" to password,
                    "phone" to phone
                )

                // Call the sign-up function
                signUpUser(userData)
            }
        }
    }

    private fun signUpUser(userData: Map<String, String>) {
        // Use Coroutines to perform the network request on a background thread
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Make the API call
                val response: Response<Map<String, String>> = RetrofitClient.apiService.signUpUser(userData)

                // Switch to the main thread to update the UI
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null && responseBody["message"] == "User added successfully") {
                            // Sign-up successful
                            Toast.makeText(this@SignUpActivity, "Sign-up successful!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish() // Close the SignUpActivity
                        } else {
                            // Handle unexpected response
                            Toast.makeText(this@SignUpActivity, "Unexpected response: $responseBody", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Sign-up failed
                        val errorBody = response.errorBody()?.string()
                        Toast.makeText(this@SignUpActivity, "Sign-up failed: $errorBody", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                // Handle network errors
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@SignUpActivity, "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}