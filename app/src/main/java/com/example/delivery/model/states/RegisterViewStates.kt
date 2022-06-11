package com.example.delivery.model.states

import com.example.delivery.model.createUser.ResponseHttp

sealed class RegisterViewStates
data class RegisterError(val error: String) : RegisterViewStates()
data class RegisterSuccess(val createUserResponse: ResponseHttp?) : RegisterViewStates()
data class RegisterFormat(val format: String) : RegisterViewStates()
object RegisterLoading : RegisterViewStates()
object RegisterEnabled : RegisterViewStates()
object RegisterDisabled : RegisterViewStates()
