package com.aur3liux.brohelapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aur3liux.brohelapp.model.LoginResponse
import com.aur3liux.brohelapp.repository.AuthRepository
import com.aur3liux.brohelapp.repository.Resource
import com.aur3liux.brohelapp.repository.SesionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import org.json.JSONObject
import javax.inject.Inject

class AuthViewModel @Inject constructor(private val authRepository: AuthRepository): ViewModel(){

    //Para controlar los datos del usuario
    private var _userData: MutableLiveData<LoginResponse> = MutableLiveData<LoginResponse>()
    var UserData: LiveData<LoginResponse> = _userData

    //Iniciar sesion
    fun LoginUser(context: Context, jsonObj: JSONObject):Resource<LoginResponse>{
        try {
            val result = authRepository.getLogin(
                context = context,
                jsonObj = jsonObj)
            MediatorLiveData<Resource<LoginResponse>>().apply {
                addSource(result){
                    _userData.value = it.data!!
                }
                observeForever{
                    Log.i("OBSERVER", UserData!!.value.toString())
                }
            }
            return  Resource.Error("Please wait!!")
        }catch (ex: Exception){
            return Resource.Error("Error de acceso ${ex.message}")
        }
    }
    //Registrar usuario - Pendiente
}