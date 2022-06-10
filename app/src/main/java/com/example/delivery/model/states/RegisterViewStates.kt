package com.example.delivery.model.states

import com.example.delivery.model.createUser.CreateUserResponseRemote

sealed class RegisterViewStates
data class RegisterError(val error: String) : RegisterViewStates()
data class RegisterSuccess(val createUserResponse: Boolean) : RegisterViewStates()
data class RegisterFormat(val format: String) : RegisterViewStates()
object RegisterLoading : RegisterViewStates()
object RegisterEnabled : RegisterViewStates()
object RegisterDisabled : RegisterViewStates()
