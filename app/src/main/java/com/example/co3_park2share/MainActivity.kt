package com.example.co3_park2share

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {

    private lateinit var backgroundLayout: ConstraintLayout
    private lateinit var titleText: TextView
    private lateinit var descriptionText: TextView
    private lateinit var nextButton: Button
    private lateinit var authButtonsLayout: LinearLayout
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: Button

    private var currentSlide = 0

    private val slides = listOf(
        Slide("#ADD8E6", "Welcome to Park2Share", "Where airport parking meets car sharing."),
        Slide("#D3D3D3", "Earn While You Travel", "List your car for other travelers to rent."),
        Slide("#FFFACD", "Find Cars Near the Airport", "Book quickly and easily when you land."),
        Slide("#DDA0DD", "Safe & Secure", "Every rental is covered with insurance.")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        backgroundLayout = findViewById(R.id.backgroundLayout)
        titleText = findViewById(R.id.titleText)
        descriptionText = findViewById(R.id.descriptionText)
        nextButton = findViewById(R.id.nextButton)
        authButtonsLayout = findViewById(R.id.authButtonsLayout)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSignUp)

        updateSlide()

        nextButton.setOnClickListener { showNextSlide() }
        backgroundLayout.setOnClickListener { showNextSlide() }

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val btnSignUp = findViewById<Button>(R.id.btnSignUp)
        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showNextSlide() {
        currentSlide = (currentSlide + 1) % slides.size
        updateSlide()
    }

    private fun updateSlide() {
        val slide = slides[currentSlide]
        backgroundLayout.setBackgroundColor(android.graphics.Color.parseColor(slide.color))
        titleText.text = slide.title
        descriptionText.text = slide.description

        // Show buttons only on the last slide
        if (currentSlide == slides.size - 1) {
            authButtonsLayout.visibility = android.view.View.VISIBLE
            nextButton.visibility = android.view.View.GONE
        } else {
            authButtonsLayout.visibility = android.view.View.GONE
            nextButton.visibility = android.view.View.VISIBLE
        }
    }

    data class Slide(val color: String, val title: String, val description: String)
}
