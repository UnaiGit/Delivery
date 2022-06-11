package com.example.delivery.activities.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.delivery.model.effects.LoginOpenRegister
import com.example.delivery.model.effects.LoginViewEffects
import com.example.delivery.model.effects.RegisterViewEffects
import com.example.delivery.model.states.*

class LoginViewModel() : ViewModel(){
    private var isEmailValid = false
    private var isPasswordValid = false

    private val _viewState: MutableLiveData<LoginViewStates> = MutableLiveData(LoginDisabled)
    private val _viewEffect: MutableLiveData<LoginViewEffects> = MutableLiveData()

    fun viewState(): LiveData<LoginViewStates> {
        return _viewState
    }

    fun viewEffect(): LiveData<LoginViewEffects> {
        return _viewEffect
    }

    fun inEmailTyped(email: String) {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _viewState.value = LoginFormat("")
            isEmailValid = true
        } else {
            _viewState.value = LoginFormat("Your email format is incorrect")
            isEmailValid = false
        }
        buttonLoginEnabled()
    }

    fun inPasswordTyped(password: String) {
        if (with(password){contains("[0-9]".toRegex()) && length > 8}){
            _viewState.value = LoginFormat("")
            isPasswordValid = true
        } else {
            _viewState.value = LoginFormat("Password must as lest 8 characters and numbers")
            isPasswordValid = false
        }
        buttonLoginEnabled()
    }

    private fun buttonLoginEnabled() {
        if (isEmailValid && isPasswordValid){
            _viewState.value = LoginEnabled
        }
        else {
            _viewState.value = LoginDisabled
        }
    }

    fun onLoginButtonClicked(email: String, password: String){

    }

    fun onRegisterButtonClicked(){
        _viewEffect.value = LoginOpenRegister
    }

}