package com.example.co3_park2share.viewmodel

import android.content.Context
import com.example.co3_park2share.model.Users
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences = application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    private val _loggedInUser = MutableLiveData<Users?>()
    val loggedInUser: MutableLiveData<Users?> get() = _loggedInUser

    init {
        loadUserFromPreferences()
    }

    private fun loadUserFromPreferences() {
        val userId = sharedPreferences.getInt("uid", -1)
        if (userId != -1) {
            val email = sharedPreferences.getString("email", "") ?: ""
            _loggedInUser.value = Users(
                uid = userId,
                firstName = "",
                lastName = "",
                email = email,
                password = "",
                phone = ""
            )
        }
    }

    fun saveUser(user: Users) {
        sharedPreferences.edit()
            .putInt("uid", user.uid)
            .putString("email", user.email)
            .apply()
        _loggedInUser.value = user
    }

    fun logout() {
        sharedPreferences.edit().clear().apply()
        _loggedInUser.value = null
    }
}
