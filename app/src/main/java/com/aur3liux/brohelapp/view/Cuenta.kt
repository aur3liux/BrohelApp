package com.aur3liux.brohelapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Cuenta() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Yellow, shape = RectangleShape)){
        Text(text = "Datos del perfil", Modifier.align(Alignment.Center).padding(16.dp),color = Color.Black,
            fontSize = 15.sp)
    }
}