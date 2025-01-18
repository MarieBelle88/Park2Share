package com.example.models

data class Cars(
    val cid: Int,
    val uid: Int,
    val brand: String,
    val model: String,
    val color: String,
    val plate: String,
    val location: String,
    val price: Float,
    val isAvailable: Boolean,
)