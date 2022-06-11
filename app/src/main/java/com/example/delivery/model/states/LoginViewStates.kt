package com.example.delivery.model.states

sealed class LoginViewStates
data class LoginError(val error: String) : LoginViewStates()
data class LoginSuccess(val loginUserResponse: String?) : LoginViewStates()
data class LoginFormat(val format: String) : LoginViewStates()
object LoginEnabled: LoginViewStates()
object LoginDisabled: LoginViewStates()
object LoginLoading: LoginViewStates()
