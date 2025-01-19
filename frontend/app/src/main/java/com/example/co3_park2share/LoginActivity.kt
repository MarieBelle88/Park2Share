package com.example.co3_park2share

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.co3_park2share.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()


        // Initialize View Binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listener on the Log In button
        binding.btnLoginSubmit.setOnClickListener {
            val email = binding.etLoginEmail.text.toString()
            val password = binding.etLoginPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Implement authentication logic here
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                // Navigate to Dashboard or another page
                val intent = Intent(this, HomePageActivity::class.java)
                startActivity(intent)
                finish() // Ensure LoginActivity is removed from the backstack
            }
        }
    }
}
