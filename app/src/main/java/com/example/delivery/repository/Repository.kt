package com.example.delivery.repository

import com.example.delivery.api.RetrofitInstance
import com.example.delivery.model.createUser.CreateUser

class Repository {

    companion object {
        const val NO_INTERNET_ERROR = 1200
    }

    suspend fun createUser(email: String, name: String, lastname: String, phone: String, password: String): Response<String> {
        try {
            val answerRetrofit =
                RetrofitInstance.api.registerUser(CreateUser())
            with(answerRetrofit) {
                return if (isSuccessful) {
                    body()?.let {
                        Response(data = it.toGameshow().id)
                    } ?: Response(error = ErrorResponse(code()))
                } else {
                    Response(error = ErrorResponse(code()))
                }
            }
        } catch (e: Exception) {
            return Response(error = ErrorResponse(NO_INTERNET_ERROR))
        }
    }
}