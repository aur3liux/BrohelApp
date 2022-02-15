package com.aur3liux.brohelapp.viewmodel

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import com.aur3liux.brohelapp.repository.SesionRepository
import kotlinx.coroutines.flow.MutableStateFlow

class SessionViewModel: ViewModel(){
    private val _estadoSesion: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val SesionState: MutableStateFlow<Boolean?> = _estadoSesion

    //Verificar inicio de sesion
    @Composable
    fun TestLogIn(context: Context){
        val dataStore = SesionRepository(context = context)
        _estadoSesion.value = dataStore.getLogInStatus.collectAsState(false).value
    }
}