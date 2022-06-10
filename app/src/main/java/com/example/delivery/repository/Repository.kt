package com.example.delivery.repository

import android.util.Log
import com.example.delivery.api.RetrofitInstance
import com.example.delivery.model.createUser.CreateUser
import com.example.delivery.model.createUser.CreateUserResponseRemote

class Repository {

    companion object {
        const val NO_INTERNET_ERROR = 1200
    }

    suspend fun createNewUser(email: String, name: String, lastname: String, phone: String, password: String): Response<Boolean> {
        try {
            val answerRetrofit = RetrofitInstance.api.registerUser(CreateUser(email = email, name = name, lastname = lastname, phone = phone, password = password))
            println(answerRetrofit.message())
            with(answerRetrofit) {
                return if (isSuccessful) {
                    body()?.let {
                        Response(data = it.toStatus().isSuccess)
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