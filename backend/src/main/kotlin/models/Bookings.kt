package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Bookings(
    val bid: Int,
    val cid: Int,
    val uid: Int,
    val start: String,
    val end: String,
    val total: Float,
    val status: String,
)