package com.example.delivery.activities.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.delivery.model.effects.RegisterViewEffects
import com.example.delivery.model.states.RegisterViewStates

class RegisterViewModel(private val repository: Repository): ViewModel() {
    private val _viewState: MutableLiveData<RegisterViewStates> = MutableLiveData(MainDisabled)
    private val _viewEffect: MutableLiveData<RegisterViewEffects> = MutableLiveData()

    fun viewState(): LiveData<RegisterViewStates> {
        return _viewState
    }

    fun viewEffect(): LiveData<RegisterViewEffects> {
        return _viewEffect
    }


}