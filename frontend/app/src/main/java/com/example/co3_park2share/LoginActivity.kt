package com.example.co3_park2share

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.co3_park2share.databinding.ActivityLoginBinding
import com.example.co3_park2share.model.Users
import com.example.co3_park2share.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val uid = sharedPreferences.getInt("uid", -1)

        if (uid != -1) {
            startActivity(Intent(this, HomePageActivity::class.java))
            finish()
            return
        }

        binding.btnLoginSubmit.setOnClickListener {
            val email = binding.etLoginEmail.text.toString()
            val password = binding.etLoginPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(email, password)
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        val credentials = mapOf("email" to email, "password" to password)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: Response<Users> = RetrofitClient.apiService.loginUser(credentials)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        val user = response.body()!!

                        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                        sharedPreferences.edit()
                            .putInt("uid", user.uid)
                            .apply()

                        Toast.makeText(this@LoginActivity, "Login successful!", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@LoginActivity, HomePageActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Toast.makeText(this@LoginActivity, "Login failed: $errorBody", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@LoginActivity, "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
