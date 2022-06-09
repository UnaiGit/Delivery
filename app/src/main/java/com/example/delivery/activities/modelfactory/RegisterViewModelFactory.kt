package com.example.delivery.activities.modelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.delivery.activities.viewmodel.RegisterViewModel
import com.example.delivery.repository.Repository

class RegisterViewModelFactory (
    private val repository: Repository
    ) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterViewModel(repository) as T
    }
}