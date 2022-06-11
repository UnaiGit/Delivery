package com.example.delivery.activities.screen

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.delivery.R
import com.example.delivery.activities.client.home.ClientHomeActivity
import com.example.delivery.model.viewmodel.LoginViewModel
import com.example.delivery.databinding.ActivityLoginBinding
import com.example.delivery.model.createUser.ResponseHttp
import com.example.delivery.model.createUser.User
import com.example.delivery.model.effects.LoginOpenClientHome
import com.example.delivery.model.effects.LoginOpenRegister
import com.example.delivery.model.states.*
import com.example.delivery.popup.PopupWarningLayout
import com.example.delivery.utils.SharedPref
import com.google.gson.Gson

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        viewModel.viewState().observe(this) { state ->
            when (state) {
                is LoginEnabled -> showLoginButtonEnabled()
                is LoginDisabled -> showAllByDefault()
                is LoginLoading -> showProgressBar()
                is LoginError -> showErrorPopup(state.error)
                is LoginSuccess -> showSuccessLogin(state.loginUserResponse)
                is LoginFormat -> showFormatError(state.format)
            }
        }

        viewModel.viewEffect().observe(this) { effect ->
            when(effect){
                is LoginOpenRegister -> openRegisterSelected()
                is LoginOpenClientHome -> openClientHomeSelected()
            }
        }

        getUserFromPreference()

        binding.etEmail.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {
                viewModel.inEmailTyped(text.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.etPassword.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {
                viewModel.inPasswordTyped(text.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnLogin.setOnClickListener{
            viewModel.onLoginButtonClicked(binding.etEmail.text.toString(),binding.etPassword.text.toString())
        }

        binding.btnRegister.setOnClickListener{
            viewModel.onRegisterButtonClicked()
        }

    }

    private fun showFormatError(format: String) {
        binding.tvError.text = format
    }

    private fun showSuccessLogin(loginUserResponse: ResponseHttp?) {
        saveUserInSession(loginUserResponse?.data.toString())
        Toast.makeText(this, loginUserResponse?.message, Toast.LENGTH_SHORT).show()
        viewModel.onLoadClientHome()
        binding.btnLogin.isVisible = true
        binding.pbLogin.isVisible = false
    }

    private fun showErrorPopup(error: String) {
        PopupWarningLayout.showPopup(this, error)
        binding.btnRegister.isVisible = true
        binding.pbLogin.isVisible = false
    }

    private fun showProgressBar() {
        binding.pbLogin.isVisible = true
        binding.btnLogin.isVisible = false
    }

    private fun showAllByDefault() {
        binding.btnLogin.isEnabled = false
        binding.btnLogin.setBackgroundColor(Color.GRAY)
    }

    private fun showLoginButtonEnabled() {
        binding.btnLogin.isEnabled =true
        binding.btnLogin.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_main))
    }

    private fun openRegisterSelected(){
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun openClientHomeSelected() {
        startActivity(Intent(this, ClientHomeActivity::class.java))
    }

    private fun saveUserInSession(data: String) {
        val sharedPref = SharedPref(this)
        val gson = Gson()
        val user = gson.fromJson(data, User::class.java)
        sharedPref.save("user", user)
    }

    private fun getUserFromPreference(){
        val sharedPref = SharedPref(this)
        val gson = Gson()
        if (!sharedPref.getData("user").isNullOrBlank()){
            val user = gson.fromJson(sharedPref.getData("user"), User::class.java)
            viewModel.onLoadClientHome()
        }
    }
}