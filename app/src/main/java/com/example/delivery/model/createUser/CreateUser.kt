package com.example.delivery.model.createUser

import com.google.gson.annotations.SerializedName
data class CreateUser(
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
    @SerializedName("lastname") val lastname: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("password") val password: String
)
