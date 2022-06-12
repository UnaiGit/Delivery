package com.example.delivery.model.effects

sealed class LoginViewEffects
object LoginOpenRegister : LoginViewEffects()
object LoginOpenClientHome : LoginViewEffects()
object LoginOpenShopHome : LoginViewEffects()
object LoginOpenDeliveryHome : LoginViewEffects()
object LoginOpenSelectRoles : LoginViewEffects()

