package com.aur3liux.brohelapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.aur3liux.brohelapp.R

val QuatroSlabRegular = FontFamily(Font(R.font.quatro_slab_regular))
val QuatroSlabItalica = FontFamily(Font(R.font.quatro_slab_medium_italic))

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    caption = TextStyle(
        fontFamily = QuatroSlabItalica,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),

    h2 = TextStyle(
        fontFamily = QuatroSlabRegular,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    ),

    button = TextStyle(
        fontFamily = QuatroSlabRegular,
        fontWeight = FontWeight.W500,
        fontSize = 19.sp
    ),

    subtitle1 = TextStyle(
        fontFamily = QuatroSlabRegular,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp
    )
)