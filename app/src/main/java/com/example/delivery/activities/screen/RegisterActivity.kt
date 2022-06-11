package com.example.delivery.activities.screen

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.delivery.activities.client.home.ClientHomeActivity
import com.example.delivery.model.viewmodel.RegisterViewModel
import com.example.delivery.databinding.ActivityRegisterBinding
import com.example.delivery.model.createUser.ResponseHttp
import com.example.delivery.model.createUser.User
import com.example.delivery.model.effects.RegisterOpenClientHome
import com.example.delivery.model.effects.RegisterOpenLogin
import com.example.delivery.model.effects.RegisterOpenTerms
import com.example.delivery.model.states.*
import com.example.delivery.popup.PopupWarningLayout
import com.example.delivery.utils.SharedPref
import com.google.gson.Gson

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        viewModel.viewState().observe(this) { state ->
            when (state) {
                is RegisterEnabled -> showButtonEnabled()
                is RegisterDisabled -> showAllByDefault()
                is RegisterLoading -> showProgressBar()
                is RegisterError -> showErrorPopup(state.error)
                is RegisterSuccess -> showSuccessRegister(state.createUserResponse)
                is RegisterFormat -> showFormatError(state.format)
            }
        }

        viewModel.viewEffect().observe(this) { effect ->
            when (effect) {
                is RegisterOpenTerms -> openHomeSelected()
                is RegisterOpenLogin -> openLoginSelected()
                is RegisterOpenClientHome -> openClientHomeSelected()
            }
        }

        binding.etEmail.addTextChangedListener("inEmailTyped")
        binding.etMobile.addTextChangedListener("inPhoneTyped")
        binding.etPassword.addTextChangedListener("inPasswordTyped")

        binding.btnRegister.setOnClickListener {
            viewModel.onRegisterButtonClicked(
                email = binding.etEmail.text.toString(),
                name = binding.etName.text.toString(),
                lastname = binding.etSurname.text.toString(),
                phone = binding.etMobile.text.toString(),
                password = binding.etPassword.text.toString()
            )
        }

        binding.btnLogin.setOnClickListener {
            viewModel.onLoginButtonClicked()
        }
    }

    private fun showFormatError(format: String) {
        binding.tvError.text = format
    }

    private fun EditText.addTextChangedListener(afterChanged: String) {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {
                val variableType = text.toString()
                when (afterChanged) {
                    "inEmailTyped" -> viewModel.inEmailTyped(variableType)
                    "inPhoneTyped" -> viewModel.inPhoneTyped(variableType)
                    "inPasswordTyped" -> viewModel.inPasswordTyped(variableType)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun openLoginSelected() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun openHomeSelected() {
        TODO("Not yet implemented")
    }

    private fun showSuccessRegister(createUserResponse: ResponseHttp?) {
        Toast.makeText(this, createUserResponse?.message, Toast.LENGTH_SHORT).show()
        saveUserInSession(createUserResponse?.data.toString())
        binding.btnRegister.isVisible = true
        binding.pbRegister.isVisible = false
    }

    private fun showErrorPopup(error: String) {
        PopupWarningLayout.showPopup(this, error)
        binding.btnRegister.isVisible = true
        binding.pbRegister.isVisible = false
    }

    private fun showProgressBar() {
        binding.btnRegister.isVisible = false
        binding.pbRegister.isVisible = true
    }

    private fun showAllByDefault() {
        binding.btnRegister.isEnabled = false
        binding.btnRegister.setBackgroundColor(Color.GRAY)
    }

    private fun showButtonEnabled() {
        binding.btnRegister.isEnabled = true
        binding.btnRegister.setBackgroundColor(Color.BLUE)
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
}