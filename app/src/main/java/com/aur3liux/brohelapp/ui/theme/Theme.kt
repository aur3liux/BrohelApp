package com.aur3liux.brohelapp.ui.theme

import android.telephony.SignalStrengthUpdateRequest
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val surfaceColorLigth = Color(0xFF3C489A)
val surfaceColorDark = Color(0xFFC4C4C4)
val primaryLight = Color(0xFFFFFFFF)
val primaryDark = Color(0xFF000000)
val secondaryLight = Color(0xFFC4C4C4)
val secondaryDark = Color(0xFF9E9E9E)

private val DarkColorPalette = darkColors(
    surface = surfaceColorDark,
    secondary = secondaryDark,
    primary = primaryDark,
    primaryVariant = Purple700,
)

private val LightColorPalette = lightColors(
    surface = surfaceColorLigth,
    secondary = secondaryLight,
    primary = primaryLight,
    primaryVariant = Purple700,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun BrohelAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}