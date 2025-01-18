package com.example.models

import java.sql.Date

data class Bookings(
    val bid: Int,
    val cid: Int,
    val uid: Int,
    val start: Date,
    val end: Date,
    val total: Float,
    val status: String,
)