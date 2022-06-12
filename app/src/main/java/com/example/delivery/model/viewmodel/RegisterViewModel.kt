package com.example.delivery.model.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.delivery.model.createUser.User
import com.example.delivery.model.createUser.ResponseHttp
import com.example.delivery.model.effects.RegisterOpenClientHome
import com.example.delivery.model.effects.RegisterOpenLogin
import com.example.delivery.model.effects.RegisterViewEffects
import com.example.delivery.model.states.*
import com.example.delivery.provider.UsersProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel() : ViewModel() {
    private var isEmailValid = false
    private var isPhoneValid = false
    private var isPasswordValid = false
    var usersProvider = UsersProvider()

    private val _viewState: MutableLiveData<RegisterViewStates> = MutableLiveData(RegisterDisabled)
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
            _viewState.value = RegisterFormat("")
            isEmailValid = true
        } else {
            _viewState.value = RegisterFormat("Your email format is incorrect")
            !isEmailValid
        }
        buttonRegisterEnabled()
    }

    fun inPhoneTyped(phone: String) {
        if (phone.length == 9) {
            _viewState.value = RegisterFormat("")
            isPhoneValid = true
        } else {
            _viewState.value = RegisterFormat("Phone must have 9 Digits")
            !isPhoneValid
        }
        buttonRegisterEnabled()
    }

    fun inPasswordTyped(password: String) {
        if (with(password){contains("[0-9]".toRegex()) && length > 8}){
                _viewState.value = RegisterFormat("")
                isPasswordValid = true
            } else {
            _viewState.value = RegisterFormat("Password must as lest 8 characters and numbers")
            !isPasswordValid
        }
        buttonRegisterEnabled()
    }

    private fun buttonRegisterEnabled() {
        if (isEmailValid && isPasswordValid && isPhoneValid){
            _viewState.value = RegisterEnabled
        }
        else {
            _viewState.value = RegisterDisabled
        }
    }

    /*private fun errorHandler(errorCode: ErrorResponse) {
        if (errorCode == ErrorResponse(Repository.NO_INTERNET_ERROR)) {
            _viewState.value = RegisterError("No Network connection")
        } else {
            _viewState.value = RegisterError("Unexpected Server Error. Please try again later")
        }
    }*/

    //Action Register
    fun onRegisterButtonClicked(email: String, name: String, lastname: String, phone: String, password: String) {
        val createUser = User(email = email,  name = name, lastname = lastname, phone = phone, password = password)
        usersProvider.register(createUser)?.enqueue(object: Callback<ResponseHttp>{
            override fun onResponse(
                call: Call<ResponseHttp>,
                response: Response<ResponseHttp>
            ) {
                if (response.body()?.isSuccess == true){
                    _viewState.value = RegisterSuccess(response.body())
                    _viewEffect.value = RegisterOpenClientHome
                    Log.d(TAG, "Body ${response.body()}")
                }else{
                    _viewState.value = RegisterError("The email or password is incorrect")
                }
            }

            override fun onFailure(call: Call<ResponseHttp>, t: Throwable) {
                _viewState.value = RegisterError("There was an issue on the server")
                Log.d(TAG, "Error happened ${t.message}")
            }

        })
    }

    fun onLoginButtonClicked(){
        _viewEffect.value = RegisterOpenLogin
    }
}