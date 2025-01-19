package com.example.co3_park2share

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.co3_park2share.databinding.ActivitySignUpBinding

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
            val email = binding.etSignUpEmail.text.toString()
            val password = binding.etSignUpPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                // Implement sign-up logic here
                Toast.makeText(this, "Sign-up successful!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, HomePageActivity::class.java)
                startActivity(intent)

                //finish()

               // val intent = Intent(this, CarDetailsActivity::class.java)
                //startActivity(intent)
            }
        }
    }
}
