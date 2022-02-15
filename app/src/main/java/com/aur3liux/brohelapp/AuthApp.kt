package com.aur3liux.brohelapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aur3liux.brohelapp.view.Login
import com.aur3liux.brohelapp.view.Registro

sealed class AuthScreens(val ruta: String){
    object LOGIN: AuthScreens("Login")
    object REGISTER: AuthScreens("Registro")
 }

@Composable
fun AuthApp(){
    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = AuthScreens.LOGIN.ruta
    ) {
        //LogIn
        composable(
            AuthScreens.LOGIN.ruta,
            content = {
                Login(
                    navController = navController)
            })

        //Registro
        composable(
            AuthScreens.REGISTER.ruta,
            content = {
                Registro(
                    onBack = { navController.popBackStack() })
            })
    }
}