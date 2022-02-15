package com.aur3liux.brohelapp.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PoliticaPrivacidad(stateCkecked: Boolean, onChecked:(Boolean)-> Unit) {
    val annotatedLinkString = buildAnnotatedString {
        val str = "Ver política de privacidad"
        val startIndex = 0
        val endIndex = str.length
        append(str)
        addStyle(
            style = SpanStyle(
                color = MaterialTheme.colors.primary,
                fontSize = 16.sp,
                textDecoration = TextDecoration.Underline
            ),start = startIndex, end = endIndex
        )
        addStringAnnotation(
            tag = "URL",
            annotation = "https://google.com/policy",
            start = startIndex, end = endIndex)
    }

    val uriHandler = LocalUriHandler.current
    ClickableText(text = annotatedLinkString,
        style = MaterialTheme.typography.body1,
        onClick = { offset ->
            annotatedLinkString
                .getStringAnnotations("URL", offset, offset)
                .firstOrNull()?.let {
                    uriHandler.openUri(it.item)
                }
        })
    Spacer(modifier = Modifier.height(15.dp))
    Row(modifier = Modifier.clickable { onChecked(stateCkecked)}.height(40.dp).padding(horizontal = 10.dp)) {
        Checkbox(
            checked = stateCkecked,
            onCheckedChange = {onChecked(stateCkecked)},
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colors.surface,
                uncheckedColor = MaterialTheme.colors.surface,
                checkmarkColor = MaterialTheme.colors.primary
            ),
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text("He leído y acepto los términos", color = MaterialTheme.colors.surface)
    }
}