package com.aur3liux.brohelapp.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aur3liux.brohelapp.repository.AuthRepository
import com.aur3liux.brohelapp.viewmodel.AuthViewModel
import java.lang.IllegalArgumentException

class LoginViewmodelFactory(private val authRepository: AuthRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AuthViewModel::class.java)){
            return AuthViewModel(authRepository) as T
        }
        throw  IllegalArgumentException("Viewmodel desconocido")
    }
}