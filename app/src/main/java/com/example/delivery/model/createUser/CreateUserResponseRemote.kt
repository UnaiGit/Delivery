package com.example.delivery.model.createUser

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class CreateUserResponseRemote(
    @SerializedName("success") val isSuccess: Boolean,
    @SerializedName("message") val message:String,
    @SerializedName("data") val data: String
){
    fun toStatus(): UserRegisterStatus {
        return UserRegisterStatus(isSuccess)
    }
}

data class UserRegisterStatus(
    val isSuccess: Boolean
)
