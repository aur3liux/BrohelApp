package com.aur3liux.brohelapp.repository

/* Importante agregar la dependencia
    implementation "androidx.datastore:datastore-preferences:<VERSION>"
*/
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SesionRepository(val context: Context) {
    companion object{
        private  val Context.dataStore: DataStore<Preferences> by preferencesDataStore("BrohelPreferences")
        //Indica el estado de inicio de sesion
        private val LOGIN_ESTATUS = booleanPreferencesKey("loginStatus_BH")
        //Datos del usuario
        private val EMAIL_USER = stringPreferencesKey("emailUser_BH")
        private val TOKEN_USER = stringPreferencesKey("tokenUser_BH")
    }

    // OBTENEMOS EL ESTADO DE LA SESION
    val getLogInStatus: Flow<Boolean> = context.dataStore.data.map { pref ->
        pref[LOGIN_ESTATUS] ?: false
    }

    // MODIFICAMOS EL ESTADO DE SESION
    suspend fun setSignInStatus(newState: Boolean){
        context.dataStore.edit { pref ->
            pref[LOGIN_ESTATUS] = newState
        }
    }

    // OBTENEMOS EL TOKEN DEL USUARIO
    val getTokenUser: Flow<String> = context.dataStore.data.map { pref ->
        pref[TOKEN_USER] ?: ""
    }

    // GUARDAMOS EL TOKEN DEL USUARIO
    suspend fun setTokenUser(newToken: String){
        context.dataStore.edit { pref ->
            pref[TOKEN_USER] = newToken
        }
    }

    // OBTENEMOS EL CORREO DE LA CUENTA
    val getEmailUser: Flow<String> = context.dataStore.data.map { pref ->
        pref[EMAIL_USER] ?: ""
    }

    //GUARDAMOS EL CORREO ELECTRONICO
    suspend fun setEmailUser(newEmail: String){
        context.dataStore.edit { pref ->
            pref[EMAIL_USER] = newEmail
        }
    }
}