package com.example.delivery.routes

import com.example.delivery.model.createUser.User
import com.example.delivery.model.createUser.ResponseHttp
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UsersRoutes {

    @POST("users/create")
    fun register(@Body user: User): Call<ResponseHttp>
}