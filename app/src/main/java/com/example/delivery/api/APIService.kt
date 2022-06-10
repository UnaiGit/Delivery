package com.example.delivery.api

import com.example.delivery.model.createUser.CreateUser
import com.example.delivery.model.createUser.CreateUserResponseRemote
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface APIService {
    @POST("53c70335-bcf5-43e6-bae1-afdcdb57692a")
    suspend fun registerUser(
        @Body createUser: CreateUser
    ): Response<CreateUserResponseRemote>
}