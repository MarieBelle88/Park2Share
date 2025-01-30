package com.example.co3_park2share.model

import kotlinx.serialization.Serializable

@Serializable
data class Users(
    val uid: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val phone: String
)