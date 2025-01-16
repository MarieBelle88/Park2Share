package com.example.co3_park2share

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.co3_park2share.databinding.ActivityCardetailsBinding

class CarDetailsActivity : AppCompatActivity()  {
    private lateinit var binding: ActivityCardetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivityCardetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}