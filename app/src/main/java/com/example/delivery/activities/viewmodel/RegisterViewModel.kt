package com.example.delivery.activities.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.delivery.model.effects.RegisterViewEffects
import com.example.delivery.model.states.*
import com.example.delivery.repository.ErrorResponse
import com.example.delivery.repository.Repository
import com.example.delivery.repository.Repository.Companion.NO_INTERNET_ERROR
import kotlinx.coroutines.launch
import org.xml.sax.ErrorHandler

class RegisterViewModel(private val repository: Repository) : ViewModel() {
    private var isEmailValid = false
    private var isPhoneValid = false
    private var isPasswordValid = false

    private val _viewState: MutableLiveData<RegisterViewStates> = MutableLiveData(MainDisabled)
    private val _viewEffect: MutableLiveData<RegisterViewEffects> = MutableLiveData()

    fun viewState(): LiveData<RegisterViewStates> {
        return _viewState
    }

    fun viewEffect(): LiveData<RegisterViewEffects> {
        return _viewEffect
    }

    //Validators
    fun inEmailTyped(email: String) {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isEmailValid
        } else {
            _viewState.value = RegisterFormat("Your email format is incorrect")
            !isEmailValid
        }
        buttonRegisterEnabled()
    }

    fun inPhoneTyped(phone: String) {
        if (phone.length == 9) {
            isPhoneValid
        } else {
            _viewState.value = RegisterFormat("Phone must have 9 Digits")
            !isPhoneValid
        }
        buttonRegisterEnabled()
    }

    fun inPasswordTyped(password: String) {
        if (with(password){contains("[0-9]".toRegex()) && length > 8}){
                passwordEqual(password = password)
            } else {
            _viewState.value = RegisterFormat("Password must as lest 8 characters and numbers")
            !isPasswordValid
        }
        buttonRegisterEnabled()
    }

    fun inPasswordVerify(passwordRepeat: String){
        passwordEqual(passwordRepeat = passwordRepeat)
    }

    //Password Repeater
    private fun passwordEqual(password: String? = null,passwordRepeat: String? = null) {
        if (passwordRepeat != passwordRepeat){
            isPasswordValid
        } else {
            _viewState.value = RegisterFormat("Passwords must match")
            !isPasswordValid
        }
    }

    private fun buttonRegisterEnabled() {
        if (isEmailValid && isPasswordValid && isPhoneValid) _viewState.value = RegisterEnabled
        else _viewState.value = RegisterDisabled
    }

    private fun errorHandler(errorCode: ErrorResponse) {
        if (errorCode == ErrorResponse(Repository.NO_INTERNET_ERROR)) {
            _viewState.value = RegisterError("No Network connection")
        } else {
            _viewState.value = RegisterError("Unexpected Server Error. Please try again later")
        }
    }

    //Action Register
    fun onRegisterButtonClicked(
        email: String,
        name: String,
        lastname: String,
        phone: String,
        password: String
    ) { _viewState.value = RegisterLoading
        viewModelScope.launch {
            val response = repository.createUser(email, name, lastname, phone, password)
            with(response){
                if (data != null){
                    _viewState.value = RegisterSuccess(data)
                } else {
                    if (error == ErrorResponse(NO_INTERNET_ERROR)){
                        _viewState.value = RegisterError("No Network connection")
                    }else{
                        _viewState.value = RegisterError("Unexpected Server Error. Please try again later")
                    }
                }
            }
        }
    }
}