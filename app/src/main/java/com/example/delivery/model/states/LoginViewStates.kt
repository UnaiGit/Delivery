package com.example.delivery.model.states

import com.example.delivery.model.createUser.ResponseHttp

sealed class LoginViewStates
data class LoginError(val error: String) : LoginViewStates()
data class LoginSuccess(val loginUserResponse: ResponseHttp?) : LoginViewStates()
data class LoginFormat(val format: String) : LoginViewStates()
object LoginEnabled: LoginViewStates()
object LoginDisabled: LoginViewStates()
object LoginLoading: LoginViewStates()
