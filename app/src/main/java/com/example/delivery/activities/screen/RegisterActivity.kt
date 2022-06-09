package com.example.delivery.activities.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.delivery.activities.modelfactory.RegisterViewModelFactory
import com.example.delivery.activities.viewmodel.RegisterViewModel
import com.example.delivery.databinding.ActivityRegisterBinding
import com.example.delivery.model.effects.RegisterOpenHome
import com.example.delivery.model.effects.RegisterOpenLogin
import com.example.delivery.model.states.*
import com.example.delivery.repository.Repository

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val repository = Repository()
        val viewModelFactory = RegisterViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory)[RegisterViewModel::class.java]
        viewModel.viewState().observe(this){ state ->
            when (state) {
                is RegisterEnabled -> showButtonEnabled()
                is RegisterDisabled -> showAllByDefault()
                is RegisterLoading -> showProgressBar()
                is RegisterError -> showErrorPopup()
                is RegisterSuccess -> showSuccessRegister()
            }
        }

        viewModel.viewEffect().observe(this){ effect ->
            when (effect) {
                is RegisterOpenHome -> openHomeSelected()
                is RegisterOpenLogin -> openLoginSelected()
            }
        }
    }

    private fun openLoginSelected() {
        TODO("Not yet implemented")
    }

    private fun openHomeSelected() {
        TODO("Not yet implemented")
    }

    private fun showSuccessRegister() {
        TODO("Not yet implemented")
    }

    private fun showErrorPopup() {
        TODO("Not yet implemented")
    }

    private fun showProgressBar() {
        TODO("Not yet implemented")
    }

    private fun showAllByDefault() {
        TODO("Not yet implemented")
    }

    private fun showButtonEnabled() {
        TODO("Not yet implemented")
    }
    private fun onLoginSelected() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}