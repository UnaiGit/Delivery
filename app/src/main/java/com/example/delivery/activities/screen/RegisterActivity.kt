package com.example.delivery.activities.screen

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.delivery.activities.modelfactory.RegisterViewModelFactory
import com.example.delivery.activities.viewmodel.RegisterViewModel
import com.example.delivery.databinding.ActivityRegisterBinding
import com.example.delivery.databinding.LayoutPopupWarningBinding
import com.example.delivery.model.effects.RegisterOpenHome
import com.example.delivery.model.effects.RegisterOpenLogin
import com.example.delivery.model.states.*
import com.example.delivery.popup.PopupWarningLayout
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
                is RegisterError -> showErrorPopup(state.error)
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
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun openHomeSelected() {
        TODO("Not yet implemented")
    }

    private fun showSuccessRegister() {
        Toast.makeText(this, "success",Toast.LENGTH_SHORT).show()
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
}