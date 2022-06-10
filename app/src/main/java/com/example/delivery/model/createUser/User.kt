package com.example.delivery.model.createUser

import com.google.gson.annotations.SerializedName
data class User(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String,
    @SerializedName("lastname") val lastname: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("password") val password: String,
    @SerializedName("image") val image: String? = null,
    @SerializedName("session_token") val sessionToken: String? = null,
    @SerializedName("is_available") val isAvailable: Boolean? = null
){
    override fun toString(): String {
        return "CreateUser(id=$id, email='$email', name='$name', lastname='$lastname', phone='$phone', password='$password', image=$image, sessionToken=$sessionToken, isAvailable=$isAvailable)"
    }
}