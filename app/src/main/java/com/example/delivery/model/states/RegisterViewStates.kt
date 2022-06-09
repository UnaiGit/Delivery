package com.example.delivery.model.states

import com.example.delivery.model.createUser.CreateUserResponseRemote

sealed class RegisterViewStates
data class RegisterError(val error: String) : RegisterViewStates()
data class RegisterSuccess(val createUserResponse: CreateUserResponseRemote) : RegisterViewStates()
object RegisterLoading : RegisterViewStates()
object RegisterEnabled : RegisterViewStates()
object RegisterDisabled : RegisterViewStates()