package com.example.delivery.model.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.delivery.model.createUser.ResponseHttp
import com.example.delivery.model.effects.LoginOpenClientHome
import com.example.delivery.model.effects.LoginOpenRegister
import com.example.delivery.model.effects.LoginOpenSelectRoles
import com.example.delivery.model.effects.LoginViewEffects
import com.example.delivery.model.states.*
import com.example.delivery.provider.UsersProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel() : ViewModel(){
    private var isEmailValid = false
    private var isPasswordValid = false
    private var usersProvider = UsersProvider()
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
        usersProvider.login(email,password)?.enqueue(object: Callback<ResponseHttp>{
            override fun onResponse(call: Call<ResponseHttp>, response: Response<ResponseHttp>) {
                if (response.body()?.isSuccess == true){
                    _viewState.value = LoginSuccess(response.body())
                }else{
                    _viewState.value = LoginError("The email or password is incorrect")
                }
            }

            override fun onFailure(call: Call<ResponseHttp>, t: Throwable) {
                _viewState.value = LoginError("There was an issue on the server")
            }

        })
    }

    fun onLoadClientHome(){
        _viewEffect.value = LoginOpenClientHome
    }

    fun onRegisterButtonClicked(){
        _viewEffect.value = LoginOpenRegister
    }

    fun onLoadSelectRole(){
        _viewEffect.value = LoginOpenSelectRoles
    }
}