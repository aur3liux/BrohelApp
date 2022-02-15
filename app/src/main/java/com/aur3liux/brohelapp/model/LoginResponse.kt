package com.aur3liux.brohelapp.model

//Valor esperado que responda la API rest consumida tanto en el LOGIN como en el REGISTRO
data class LoginResponse(
    val estatusCode: Int,
    val eMail: String,
    val idUsuarioToken: String)