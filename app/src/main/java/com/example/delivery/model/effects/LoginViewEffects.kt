package com.example.delivery.model.effects

sealed class LoginViewEffects
object LoginOpenRegister : LoginViewEffects()
object LoginOpenClientHome : LoginViewEffects()
object LoginOpenSelectRoles : LoginViewEffects()

