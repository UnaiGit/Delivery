package com.example.delivery.model.states

sealed class RegisterViewStates
data class RegisterError(val error: String) : RegisterViewStates()
data class RegisterSuccess(val createUserResponse: String?) : RegisterViewStates()
data class RegisterFormat(val format: String) : RegisterViewStates()
object RegisterLoading : RegisterViewStates()
object RegisterEnabled : RegisterViewStates()
object RegisterDisabled : RegisterViewStates()
