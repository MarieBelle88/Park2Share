package com.example.co3_park2share

import kotlinx.serialization.Serializable

@Serializable
data class Booking(
    val bid: Int,
    val cid: Int,
    val uid: Int,
    val brand: String,
    val model: String,
    val color: String,
    val plate: String,
    val capacity: Int,
    val location: String,
    val price: Float
)