package com.example.co3_park2share

import kotlinx.serialization.Serializable

@Serializable
data class Listing(
    val cid: Int,
    val uid: Int,
    val brand: String,
    val model: String,
    val color: String,
    val plate: String,
    val capacity: Int,
    val location: String,
    val price: Float,
    val isAvailable: Boolean
)