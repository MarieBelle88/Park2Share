package com.example.co3_park2share

import com.example.co3_park2share.model.Users
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/users/login") // The endpoint for user login
    suspend fun loginUser(@Body credentials: Map<String, String>): Response<Users>


    @POST("/users")
    suspend fun signUpUser(@Body userData: Map<String, String>): Response<Map<String, String>>

    @POST("/cars")
    suspend fun addCar(@Body carData: Map<String, String>): Response<Map<String, String>>
}