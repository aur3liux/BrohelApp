package com.aur3liux.brohelapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aur3liux.brohelapp.BrohelApp
import com.aur3liux.brohelapp.factory.LoginViewmodelFactory
import com.aur3liux.brohelapp.repository.AuthRepository
import com.aur3liux.brohelapp.repository.SesionRepository
import com.aur3liux.brohelapp.view.components.CustomInput
import com.aur3liux.brohelapp.viewmodel.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject


@Composable
fun Registro(
    onBack:()->Unit) {

    val Email = rememberSaveable{ mutableStateOf("") }
    val Nombre = rememberSaveable{ mutableStateOf("") }
    val Telefono = rememberSaveable{ mutableStateOf("") }
    val Direccion = rememberSaveable{ mutableStateOf("") }
    val textoBotonLogin = remember{ mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val scaffoldState = rememberScaffoldState()
    val onError = remember{ mutableStateOf(false) }
    val errorMsg = remember{ mutableStateOf("") }
    val onProccesing = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val dataStore = SesionRepository(context)
    val authViewModel: AuthViewModel = viewModel(
        factory = LoginViewmodelFactory(authRepository = AuthRepository())
    )

    val userSesionState = remember(authViewModel) {authViewModel.UserData}.observeAsState() //LiveData

    Scaffold(modifier = Modifier.fillMaxSize(),
        snackbarHost =  {
            SnackbarHost(hostState = it){data ->
                Snackbar(
                    actionColor = Color.Yellow,
                    contentColor = Color.White,
                    backgroundColor = Color.Red,
                    snackbarData = data)
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text("Registro de datos",
                    color = MaterialTheme.colors.primary)
                },
                backgroundColor = MaterialTheme.colors.surface,
                navigationIcon = {
                    IconButton(onClick = {onBack()}) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            tint = MaterialTheme.colors.primary,
                            contentDescription = "")
                    }
                })//TopAppBar
        },
        scaffoldState = scaffoldState) {
        Column() {

            Column(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Bienvenido",
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.primaryVariant
                )

                CustomInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    textLabel = "Correo electrónico",
                    textValue = Email,
                    backgroundColor = MaterialTheme.colors.surface,
                    keyboardType = KeyboardType.Email,
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Next)
                        }
                    ),
                    imeAction = ImeAction.Next,
                    maxLenght = 40,
                )

                CustomInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    textLabel = "Nombre",
                    textValue = Nombre,
                    backgroundColor = MaterialTheme.colors.surface,
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text,
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Next)
                        }
                    ),
                    imeAction = ImeAction.Next,
                    maxLenght = 40,
                )
                CustomInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    textLabel = "Teléfono",
                    textValue = Telefono,
                    keyboardType = KeyboardType.Number,
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Next)
                        }
                    ),
                    imeAction = ImeAction.Next,
                    maxLenght = 10,
                )

                CustomInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    textLabel = "Dirección",
                    textValue = Direccion,
                    capitalization = KeyboardCapitalization.Sentences,
                    keyboardType = KeyboardType.Email,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.moveFocus(FocusDirection.Next)
                        }
                    ),
                    imeAction = ImeAction.Next,
                    maxLenght = 40,
                )

                Spacer(modifier = Modifier.height(15.dp))
                //Boton iniciar sesion
                Button(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(300.dp)
                        .height(80.dp)
                        .padding(vertical = 10.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
                    enabled = !onProccesing.value,
                    onClick = {
                        if (isEmptyData(Email.value, Nombre.value, Telefono.value, Direccion.value)) {
                            onError.value = true
                            errorMsg.value = "Todos los datos son obligatorios"
                        } else {
                            onProccesing.value = true
                            GlobalScope.launch(Dispatchers.Main) {
                                val jsonObj = JSONObject()
                                jsonObj.put("Email", Email.value)
                                jsonObj.put("password", "")
                                jsonObj.put("Nombre", Nombre.value)
                                jsonObj.put("celular", Telefono.value)
                                jsonObj.put("direccion", Direccion.value)
                                jsonObj.put("TipoUsuario", 1)
                                //registerViewModel.getUserDataRegistro(context = context, jsonObj = jsonObj)
                                delay(700)
                            }//GlobalScope
                        }
                    }) {
                    Text(
                        text = textoBotonLogin.value,
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.button
                    )
                    if (onProccesing.value) {
                        textoBotonLogin.value = "Registrando datos"
                        ShowProgressBar()
                    } else
                        textoBotonLogin.value = "Registrar datos"
                }//Button


                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Estos datos son importantes para informarle del estatus de una queja",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.primaryVariant
                )

            }//Column
        }// Column

        //Observable
        if(onProccesing.value){
            userSesionState.value?.let {
                onProccesing.value = false
                if(userSesionState.value!!.estatusCode == 200) {
                    GlobalScope.launch {
                        dataStore.setSignInStatus(true)
                        dataStore.setTokenUser(userSesionState.value!!.idUsuarioToken)
                        dataStore.setEmailUser(Email.value)
                    }
                   BrohelApp()
                } else{
                    onProccesing.value = false
                    ProcessingError(estatusCode = userSesionState.value!!.estatusCode,
                        onError = onError,
                        errorMsg = errorMsg)
                }
            }
        }//if

        if(onError.value){
            LaunchedEffect(key1 = Unit, block = {
                delay(700)
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

//Nos ayuda a verificar que algún dato no esté veacío
fun isEmptyData(email: String, nombre: String, telefono: String, direccion: String ):Boolean{
    var resultado = false
    resultado = email.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || direccion.isEmpty()
    return  resultado
}
