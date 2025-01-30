package com.example.co3_park2share

import com.example.co3_park2share.model.Users
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("/users/login")
    suspend fun loginUser(@Body credentials: Map<String, String>): Response<Users>

    @POST("/users")
    suspend fun signUpUser(@Body userData: Map<String, String>): Response<Map<String, String>>

    @POST("/cars")
    suspend fun addCar(@Body carData: Map<String, String>): Response<Map<String, String>>

    @DELETE("/cars/{cid}")
    suspend fun deleteCar(@Path("cid") cid: Int): Response<String>

    @PUT("/cars/{cid}")
    suspend fun updateCar(
        @Path("cid") cid: Int,
        @Body updatedCar: Map<String, String>
    ): Response<String>

    @GET("/cars/user/{uid}")
    suspend fun getUserListings(@Path("uid") uid: Int): Response<List<Listing>>

    @GET("/cars/{cid}")
    suspend fun getCarDetails(@Path("cid") cid: Int): Response<Listing>





}
