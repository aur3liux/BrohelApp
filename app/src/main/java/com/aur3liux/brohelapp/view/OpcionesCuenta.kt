package com.aur3liux.brohelapp.view

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aur3liux.brohelapp.R
import kotlinx.coroutines.delay

//Representacion de datos
data class botonData(
    val imag: Int,
    val descripcion: String
)

//Lista de opciones
val listaOpcionesVisita = listOf(
    botonData(R.drawable.ic_protectores, "Protectores"),
    botonData(R.drawable.ic_motos, "Motos"),
    botonData(R.drawable.ic_healt, "Ficha mádica"),
)

@Composable
fun OpcionesCuenta(
    onDismiss: () -> Unit,
    onConfirmClick: () -> Unit) {

    AlertDialog(
        backgroundColor = Color.White,
        onDismissRequest = { onDismiss() },
        title = { Text("Ubicación del dato",
            color=MaterialTheme.colors.secondary,
            style = MaterialTheme.typography.subtitle1) },
        text = {

            val inScale = remember { Animatable(0f) }
            LaunchedEffect(key1 = true) {
                inScale.animateTo(
                    targetValue = 1.0f, animationSpec = tween(
                        durationMillis = 500,
                        easing = {
                            OvershootInterpolator(0.6f).getInterpolation(it)
                        })
                )
                delay(400L)
            }

            Column(
                modifier = Modifier.fillMaxWidth()
                    .background(Color.White)
                    .padding(10.dp)
                    .clickable { onDismiss },
                verticalArrangement = Arrangement.Center) {
                Text(text = "Opciones")
                LazyRow(modifier = Modifier.scale(scale = inScale.value)) {
                    items(listaOpcionesVisita) { tipoOpcion ->
                        Column(
                            modifier = Modifier
                                .background(Color.Transparent)
                                .padding(vertical = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Card(
                                modifier = Modifier.animateContentSize()
                                    .background(Color.White)
                                    .height(120.dp)
                                    .width(140.dp)
                                    .padding(5.dp)
                                    .clip(RoundedCornerShape(15.dp)),
                                elevation = 10.dp
                            ) {
                                Image(
                                    modifier = Modifier.background(Color.White),
                                    painter = painterResource(id = tipoOpcion.imag),
                                    contentDescription = "")
                            }
                            Text(
                                tipoOpcion.descripcion,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    }
                }

            }//Column
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                onClick = { onConfirmClick() }) {
                Text(text = "Aceptar",
                    color=MaterialTheme.colors.secondary,
                    style = MaterialTheme.typography.subtitle1)
            }
        })//AlertDialog
}