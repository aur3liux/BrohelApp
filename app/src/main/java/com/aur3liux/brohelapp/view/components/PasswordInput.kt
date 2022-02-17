package com.aur3liux.brohelapp.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PasswordInput(modifier: Modifier = Modifier,
                   textLabel: String,
                   textValue: MutableState<String>,
                   backgroundColor: Color = Color.White,
                   keyboardType: KeyboardType,
                   maxLenght: Int,
                  isVacio:Boolean) {

    var passwordVisibility = remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            modifier = modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colors.surface,
                backgroundColor = backgroundColor,
                placeholderColor = MaterialTheme.colors.primaryVariant,
                disabledPlaceholderColor = MaterialTheme.colors.primaryVariant,
                cursorColor = MaterialTheme.colors.secondary,   //Color del cursor
                focusedLabelColor = MaterialTheme.colors.secondaryVariant), //texto placeholder al dar push

            value = textValue.value,
            label = { Text(textLabel, style = MaterialTheme.typography.body1) },
            textStyle = TextStyle(
                fontSize = 19.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType),
            trailingIcon = {
                val image = if (passwordVisibility.value)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                IconButton(onClick = {
                    passwordVisibility.value = !passwordVisibility.value
                }) {
                    Icon(imageVector  = image, "")
                }
            },
            visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
            shape = RoundedCornerShape(10.dp),
            onValueChange = {
                if(it.length <= maxLenght){
                    textValue.value = it
                }
            })

        if(isVacio){
            Text("No deje vacío este campo",
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.primaryVariant)
        }
    }
}
