package com.aur3liux.brohelapp.view.components

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PasswordInput(modifier: Modifier = Modifier,
                textLabel: String,
                textValue: MutableState<String>,
                backgroundColor: Color = Color.White,
                keyboardType: KeyboardType,
                keyboardActions: KeyboardActions,
                capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
                imeAction: ImeAction,
                traingIcon: @Composable() (()-> Unit)? = null,
                visualTransformation: VisualTransformation = VisualTransformation.None,
                maxLenght: Int) {

    var passwordVisibility = remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black,
            backgroundColor = Color.White,
            placeholderColor = MaterialTheme.colors.primaryVariant,
            disabledPlaceholderColor = MaterialTheme.colors.primary,
            unfocusedBorderColor = MaterialTheme.colors.primaryVariant,
            unfocusedLabelColor = MaterialTheme.colors.primaryVariant,
            focusedBorderColor = MaterialTheme.colors.secondary, //lineas alrededor al dar push
            cursorColor = MaterialTheme.colors.secondary,   //Color del cursor
            focusedLabelColor = MaterialTheme.colors.secondaryVariant), //texto placeholder al dar push

        value = textValue.value,
        label = { Text(textLabel, style = MaterialTheme.typography.body1) },
        textStyle = TextStyle(
            fontSize = 19.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            capitalization = capitalization,
            imeAction = imeAction),
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
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        shape = RoundedCornerShape(10.dp),
        onValueChange = {
            if(it.length <= maxLenght){
                textValue.value = it
            }
        })
}

