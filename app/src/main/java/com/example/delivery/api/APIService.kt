package com.example.delivery.api

import com.example.delivery.model.createUser.CreateUser
import com.example.delivery.model.createUser.CreateUserResponseRemote
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface APIService {
    @POST("api/users/create")
    suspend fun registerUser(
        @Body createUser: CreateUser
    ): Response<CreateUserResponseRemote>
}