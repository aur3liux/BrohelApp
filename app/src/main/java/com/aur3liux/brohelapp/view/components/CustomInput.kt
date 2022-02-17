package com.aur3liux.brohelapp.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomInput(modifier: Modifier = Modifier,
                textLabel: String,
                textValue: MutableState<String>,
                backgroundColor: Color = Color.White,
                keyboardType: KeyboardType,
                capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
                traingIcon: @Composable() (()-> Unit)? = null,
                visualTransformation: VisualTransformation = VisualTransformation.None,
                maxLenght: Int,
                isVacio: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            modifier = modifier,
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
                keyboardType = keyboardType,
                capitalization = capitalization),
            trailingIcon = traingIcon,
            visualTransformation = visualTransformation,
            shape = RoundedCornerShape(10.dp),
            onValueChange = {
                if(it.length <= maxLenght){
                    textValue.value = it
                }
            })

        if(isVacio){
            Text("No deje vacío este dato",
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.primaryVariant)
        }
    }
}
