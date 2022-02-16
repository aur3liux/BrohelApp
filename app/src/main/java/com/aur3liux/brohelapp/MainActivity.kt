package com.aur3liux.brohelapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aur3liux.brohelapp.ui.theme.BrohelAppTheme
import com.aur3liux.brohelapp.viewmodel.SessionViewModel
import kotlinx.coroutines.delay

//Definicion de rutas para las VISTAS
sealed class InitScreens(var route: String){
    object SPLASH_SCREEN: InitScreens("splash_screen")
    object CHECK_SESION_STATE: InitScreens("check_sesion_state")
}

class MainActivity : ComponentActivity() {
    private val sessionViewModel: SessionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TransparentStatusBar(windows = window)
            val navController = rememberNavController()

            BrohelAppTheme {
                NavHost(navController = navController ,
                    startDestination = InitScreens.SPLASH_SCREEN.route){

                    //Splash Screen
                    composable(InitScreens.SPLASH_SCREEN.route){
                        SplashScreen(navController = navController)
                    }

                    //Lógica para determinar si se ha iniciado sesion
                    composable(InitScreens.CHECK_SESION_STATE.route){
                        checkSesionstate(
                            sessionViewModel = sessionViewModel)
                    }
                }//NavHost
            }
        }
    }
}

@Composable
fun checkSesionstate(
    sessionViewModel: SessionViewModel) {
    val estatus by remember{sessionViewModel.SesionState}.collectAsState()
    val context = LocalContext.current

    sessionViewModel.TestLogIn(context = context)

    estatus?.let {
        if(it){
            Log.i("SESION ACTIVA", it.toString())
        } else{
            AuthApp()
        }
    }
}


@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember { Animatable(initialValue = 0f) }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1.0f,
            animationSpec = tween(
                durationMillis = 700,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
        )
        delay(2000)
        navController.navigate(InitScreens.CHECK_SESION_STATE.route){
            popUpTo(InitScreens.SPLASH_SCREEN.route){inclusive = true}
        }
    }
    //Contenido del splash Screen
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
            Image(painter = painterResource(id = R.drawable.logo_brohel),
                contentDescription = "",
                modifier = Modifier.scale(scale.value))
            Text("BROHEL",
                color = MaterialTheme.colors.primary,
                fontSize = 21.sp)
    }
}

//Eliminamos el color poe defaulr de la barra superior de la App
@Composable
fun TransparentStatusBar(windows: Window) =
    MaterialTheme {
        windows.statusBarColor = MaterialTheme.colors.surface.toArgb()
        windows.navigationBarColor = MaterialTheme.colors.surface.toArgb()

        @Suppress("DEPRECATION")
        if (MaterialTheme.colors.surface.luminance() > 0.5f) {
            windows.decorView.systemUiVisibility = windows.decorView.systemUiVisibility or
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        @Suppress("DEPRECATION")
        if (MaterialTheme.colors.surface.luminance() > 0.5f) {
            windows.decorView.systemUiVisibility = windows.decorView.systemUiVisibility or
                    View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
    }
