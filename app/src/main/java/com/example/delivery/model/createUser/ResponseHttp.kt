package com.example.delivery.model.createUser

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class ResponseHttp(
    @SerializedName("message") val message:String,
    @SerializedName("success") val isSuccess: Boolean,
    @SerializedName("data") val data: JsonObject,
    @SerializedName("error") val error: String

){
    override fun toString(): String {
        return "CreateUserResponseRemote(isSuccess=$isSuccess, message='$message', data=$data, error='$error')"
    }
}
