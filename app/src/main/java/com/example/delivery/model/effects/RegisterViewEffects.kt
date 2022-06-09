package com.example.delivery.model.effects

sealed class RegisterViewEffects
object RegisterOpenHome : RegisterViewEffects()
object RegisterOpenLogin : RegisterViewEffects()
