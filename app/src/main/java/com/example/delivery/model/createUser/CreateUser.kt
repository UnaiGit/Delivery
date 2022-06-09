package com.example.delivery.model.createUser

import android.icu.util.ULocale
import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class CreateUser(
    @SerializedName("id") val id: String,
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
    @SerializedName("lastname") val lastname: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("image") val image: String,
    @SerializedName("password") val password: String,
    @SerializedName("is_available") val isAvailable: Boolean,
    @SerializedName("session_token") val sessionToken: String
){
    override fun toString(): String {
        return "CreateUser(id=$id, email='$email', name='$name', lastname='$lastname', phone='$phone', image='$image', password='$password', isAvailable=$isAvailable, sessionToken='$sessionToken')"
    }
}
