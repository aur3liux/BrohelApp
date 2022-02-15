package com.aur3liux.brohelapp.repository
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.aur3liux.brohelapp.model.LoginResponse
import org.json.JSONObject
import javax.inject.Inject

sealed class Resource<T>(val data:T? = null, val message:String? = null){
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(message: String?, data: T? = null): Resource<T>(data, message)
}

class AuthRepository @Inject constructor() {
    //Inicio de sesion
    fun getLogin(context: Context, jsonObj: JSONObject): MutableLiveData<Resource<LoginResponse>> {
        val _userData: MutableLiveData<Resource<LoginResponse>> = MutableLiveData<Resource<LoginResponse>>()
        val url = "URL-POST-WEBSERVICES"
        try {
            var queue = Volley.newRequestQueue(context)
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST, url, jsonObj, { response ->
                Log.i("RESPUESTA","Response: %s".format(response.toString()))
                val permiso = response.getString("result")
                val email = jsonObj.getString("Email")
                val userId = response.getString("userId")
                if(permiso == "ok"){
                    val user = LoginResponse(200, email, userId)
                    _userData.postValue(Resource.Success(user))
                } else{
                    val error = LoginResponse(0,"","")
                    _userData.postValue(Resource.Success(error))
                }
            },
                { error ->
                    val error = LoginResponse(error.networkResponse.statusCode, "", "")
                    _userData.postValue(Resource.Success(error))
                }
            )
            queue.add(jsonObjectRequest)
        } catch (e: Exception) {
            val error = LoginResponse(-1, "", "")
            _userData.postValue(Resource.Error("Error desconocido"))
        }
        return _userData
    }//getLoginRepository

    //Registro de usuario - pendiente
}