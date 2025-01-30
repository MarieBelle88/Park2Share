package com.example.co3_park2share

data class Listing(
    val cid: Int, // Ensure `cid` exists
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
