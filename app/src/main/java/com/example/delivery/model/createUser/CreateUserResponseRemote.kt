package com.example.delivery.model.createUser

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class CreateUserResponseRemote(
    @SerializedName("message") val message:String,
    @SerializedName("success") val isSuccess: Boolean,
    @SerializedName("data") val data: JsonObject
){
    fun toStatus(): UserRegisterStatus {
        return UserRegisterStatus(isSuccess)
    }
}

data class UserRegisterStatus(
    val status: Boolean
)
