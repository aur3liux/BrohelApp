package com.aur3liux.brohelapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.aur3liux.brohelapp.AuthScreens
import com.aur3liux.brohelapp.BrohelApp
import com.aur3liux.brohelapp.R
import com.aur3liux.brohelapp.factory.LoginViewmodelFactory
import com.aur3liux.brohelapp.repository.AuthRepository
import com.aur3liux.brohelapp.repository.SesionRepository
import com.aur3liux.brohelapp.view.components.CustomInput
import com.aur3liux.brohelapp.view.components.PasswordInput
import com.aur3liux.brohelapp.view.components.PoliticaPrivacidad
import com.aur3liux.brohelapp.viewmodel.AuthViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject

@Composable
fun Login(navController: NavController) {

    val context = LocalContext.current
    val onError = remember { mutableStateOf(false) }
    val onProccesing = remember{ mutableStateOf(false) }
    val errorMsg = remember { mutableStateOf("") }
    val scaffoldState = rememberScaffoldState()
    var checkAvisoPolitica = remember{ mutableStateOf(false) }
    val correo = rememberSaveable{ mutableStateOf("") }
    val password = rememberSaveable{ mutableStateOf("") }
    val dataStore = SesionRepository(context)
    val textoBotonLogin = remember{ mutableStateOf("") }

    val authViewModel: AuthViewModel = viewModel(
        factory = LoginViewmodelFactory(authRepository = AuthRepository())
    )

    val userSesionState = remember(authViewModel) {authViewModel.UserData}.observeAsState() //LiveData

    Scaffold(
        snackbarHost =  {
            SnackbarHost(hostState = it){data ->
                Snackbar(
                    actionColor = Color.Yellow,
                    contentColor = Color.White,
                    backgroundColor = Color.Red,
                    snackbarData = data)
            }
        },
        scaffoldState = scaffoldState) {
            val image: Painter = painterResource(id = R.drawable.background_paisaje)
        //Fondo de la pantalla con imagen de fondo
        Box(
                contentAlignment = Alignment.BottomCenter,
                content = {
                Image(
                    painter = image,
                    contentDescription = "",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.fillMaxHeight(),
                )

                    //Tarjeta con esquinas redondeadas para pedir datos
                    Card(
                        modifier = Modifier.height(500.dp),
                        backgroundColor = MaterialTheme.colors.secondary.copy(alpha = 0.7f),
                        elevation = 4.dp,
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                text = "Bienvenido",
                                style = MaterialTheme.typography.h4,
                                color = Color.Black
                            )
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                text = "Ingresa tus credenciales",
                                style = MaterialTheme.typography.h2,
                                color = Color.Black
                            )

                            Spacer(modifier = Modifier.height(30.dp))
                            /********** EMAIL */
                            CustomInput(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp),
                                textLabel = "Correo electrónico",
                                textValue = correo,
                                backgroundColor = MaterialTheme.colors.surface,
                                keyboardType = KeyboardType.Email,
                                keyboardActions = KeyboardActions(
                                    onNext = {

                                    }),
                                traingIcon = { Icon(Icons.Filled.Email, contentDescription = "") },
                                imeAction = ImeAction.Next,
                                maxLenght = 40)

                            Spacer(modifier = Modifier.height(10.dp))
                            /********** PASSWORD */
                            PasswordInput(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp),
                                textLabel = "Password",
                                textValue = password,
                                backgroundColor = Color.White,
                                keyboardType = KeyboardType.Password,
                                keyboardActions = KeyboardActions(
                                    onNext = {

                                    }
                                ),
                                imeAction = ImeAction.Next,
                                maxLenght = 40)

                            //Boton iniciar sesion
                            Button(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .width(300.dp)
                                    .height(80.dp)
                                    .padding(vertical = 10.dp)
                                    .clip(RoundedCornerShape(10.dp)).alpha(1.0f),
                                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
                                enabled = !onProccesing.value,
                                    onClick = {
                                        if (checkAvisoPolitica.value) {
                                            if (correo.value.isEmpty()) {
                                                onError.value = true
                                                errorMsg.value = "El correo electrónico es obligatorio"
                                            } else {
                                                onProccesing.value = true
                                            }
                                        } else {
                                            onError.value = true
                                            errorMsg.value = "Por favor lea el aviso de privacidad y márquelo como leido"
                                        }//else
                                    }) {
                                Text(
                                    text = textoBotonLogin.value,
                                    color = MaterialTheme.colors.primary,
                                    style = MaterialTheme.typography.button)
                                if (onProccesing.value) {
                                    textoBotonLogin.value = "Iniciando sesión"
                                    ShowProgressBar()
                                } else
                                    textoBotonLogin.value = "Iniciar sesión"
                            }//Button

                            Spacer(modifier = Modifier.height(20.dp))
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .clickable {
                                    if (checkAvisoPolitica.value) {
                                        navController.navigate(AuthScreens.REGISTER.ruta)
                                    } else {
                                        onError.value = true
                                        errorMsg.value = "Por favor lea el aviso de privacidad y márquelo como leido"
                                    }//else
                                },
                                horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    textAlign = TextAlign.Center,
                                    text = "¿No tienes cuenta? Regístrate",
                                    style = MaterialTheme.typography.body1,
                                    color = MaterialTheme.colors.primary)
                                Spacer(modifier = Modifier.width(30.dp))
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            PoliticaPrivacidad(
                                stateCkecked = checkAvisoPolitica.value,
                                onChecked = { checkAvisoPolitica.value = !checkAvisoPolitica.value })
                        }//Column
                }//Card
        }) //Box



        //Observable
        if(onProccesing.value){
            LaunchedEffect(key1 = Unit, block = {
                val jsonObj = JSONObject()
                jsonObj.put("Email", correo.value)
                jsonObj.put("password", password)
                authViewModel.LoginUser(context = context, jsonObj = jsonObj)
                delay(700)
            })

            userSesionState.value?.let {
                onProccesing.value = false
                if(userSesionState.value!!.estatusCode == 200) {
                    GlobalScope.launch {
                        dataStore.setSignInStatus(true)
                        dataStore.setTokenUser(userSesionState.value!!.idUsuarioToken)
                        dataStore.setEmailUser(correo.value)
                    }
                    BrohelApp()
                } else{
                    ProcessingError(estatusCode = userSesionState.value!!.estatusCode,
                        onError = onError,
                        errorMsg = errorMsg)
                }
            }
        }//if

        if(onError.value){
            LaunchedEffect(key1 = Unit, block = {
                delay(500)
                var result = scaffoldState.snackbarHostState.showSnackbar(
                    message = errorMsg.value,
                    duration = SnackbarDuration.Short,
                    actionLabel = "Aceptar")
                when(result) {
                    SnackbarResult.ActionPerformed ->{
                        onError.value = false
                        errorMsg.value = ""
                        onProccesing.value = false
                    }
                    SnackbarResult.Dismissed->{
                        onError.value = false
                        errorMsg.value = ""
                        onProccesing.value = false
                    }
                }//when
            })
        }
    }//Scaffold
}

@Composable
fun ShowProgressBar() {
    Spacer(modifier = Modifier.padding(horizontal = 16.dp))
    CircularProgressIndicator(
        modifier = Modifier
            .size(20.dp)
            .background(Color.Transparent),
        color = Color.White,
        strokeWidth = 2.dp)
}

@Composable
fun ProcessingError(estatusCode: Int, onError: MutableState<Boolean>, errorMsg: MutableState<String>){
    when(estatusCode){
        0->{
            onError.value = true
            errorMsg.value = "Sus credenciales no tienen permiso para acceder a la aplicación"
        }
        400->{
            onError.value = true
            errorMsg.value = "No es posible acceder con estos datos"
        }
        404->{
            onError.value = true
            errorMsg.value = "Servidor en mantenimiento, intente mas tarde"
        }
        500->{
            onError.value = true
            errorMsg.value = "Servidor temporalmente fuera de servicio"
        }
        else ->{
            onError.value = true
            errorMsg.value = "Ha ocurrido un error inesperado estamos trabajando para resolverlo"
        }
    }
}
