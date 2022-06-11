package com.example.delivery.activities.screen

import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.delivery.R
import com.example.delivery.activities.viewmodel.LoginViewModel
import com.example.delivery.activities.viewmodel.RegisterViewModel
import com.example.delivery.databinding.ActivityLoginBinding
import com.example.delivery.model.effects.LoginOpenRegister
import com.example.delivery.model.states.*
import com.example.delivery.popup.PopupWarningLayout

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
                is LoginSuccess -> showSuccessLogin()
                is LoginFormat -> showFormatError(state.format)
            }
        }

        viewModel.viewEffect().observe(this) { effect ->
            when(effect){
                is LoginOpenRegister -> openRegisterSelected()
            }
        }

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

    private fun showSuccessLogin() {
        Toast.makeText(this, "answer", Toast.LENGTH_SHORT).show()
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
}