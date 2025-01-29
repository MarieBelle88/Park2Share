package com.example.co3_park2share

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/users/login") // The endpoint for user login
    suspend fun loginUser(@Body credentials: Map<String, String>): Response<Users>
}