package com.example.delivery.repository

import com.example.delivery.api.RetrofitInstance
import com.example.delivery.model.createUser.CreateUser
import com.example.delivery.model.createUser.CreateUserResponseRemote

class Repository {

    companion object {
        const val NO_INTERNET_ERROR = 1200
    }

    suspend fun createUser(email: String, name: String, lastname: String, phone: String, password: String): Response<CreateUserResponseRemote> {
        try {
            val answerRetrofit =
                RetrofitInstance.api.registerUser(CreateUser(email = email, name = name, lastname = lastname, phone = phone, password = password))
            with(answerRetrofit) {
                return if (isSuccessful) {
                    body()?.let {
                        Response(data = it)
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