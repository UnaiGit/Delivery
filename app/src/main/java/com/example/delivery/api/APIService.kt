package com.example.delivery.api

import com.example.delivery.model.createUser.CreateUser
import com.example.delivery.model.createUser.CreateUserResponseRemote
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface APIService {
    @POST("d0e03a0b-ce2a-4dc6-87f2-db26d00fd6a7")
    suspend fun registerUser(
        @Body createUser: CreateUser
    ): Response<CreateUserResponseRemote>
}