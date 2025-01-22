package com.example.co3_park2share

import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    private lateinit var notificationsSwitch: Switch
    private lateinit var darkModeSwitch: Switch
    private lateinit var languageSpinner: Spinner
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Initialize views
        notificationsSwitch = findViewById(R.id.setting1Switch)
        darkModeSwitch = findViewById(R.id.setting2Switch)
        languageSpinner = findViewById(R.id.setting3Spinner)
        saveButton = findViewById(R.id.saveButton)

        // Load saved settings (if any)
        val sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE)
        notificationsSwitch.isChecked = sharedPreferences.getBoolean("notifications", false)
        darkModeSwitch.isChecked = sharedPreferences.getBoolean("darkMode", false)
        val savedLanguage = sharedPreferences.getString("language", "")
        if (savedLanguage != null) {
            val spinnerAdapter = languageSpinner.adapter
            for (i in 0 until spinnerAdapter.count) {
                if (spinnerAdapter.getItem(i).toString() == savedLanguage) {
                    languageSpinner.setSelection(i)
                    break
                }
            }
        }

        // Save settings when the save button is clicked
        saveButton.setOnClickListener {
            val notificationsEnabled = notificationsSwitch.isChecked
            val darkModeEnabled = darkModeSwitch.isChecked
            val selectedLanguage = languageSpinner.selectedItem.toString()

            // Save settings to SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putBoolean("notifications", notificationsEnabled)
            editor.putBoolean("darkMode", darkModeEnabled)
            editor.putString("language", selectedLanguage)
            editor.apply()

            // Show confirmation message
            Toast.makeText(this, "Settings saved!", Toast.LENGTH_SHORT).show()
        }
    }
}
