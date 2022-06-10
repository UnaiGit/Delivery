package com.example.delivery.routes

import com.example.delivery.api.RetrofitClient

class APIRoutes {
    val API_URL = "http://192.168.1.71:3000/api/"
    val retrofit = RetrofitClient()

    fun getUsersRoutes(): UsersRoutes{
        return retrofit.getClient(API_URL).create(UsersRoutes::class.java)
    }
}