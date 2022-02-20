package com.aur3liux.brohelapp.view.components.floatingmenu

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Stable

interface ItemIcon {
    @Stable
    val iconRes: Int
    @Stable val iconRotate: Float?
}

private class FabIconImpl(
    override  val iconRes: Int,
    override  val iconRotate: Float?
): ItemIcon

fun FabIcon(@DrawableRes iconRes: Int, iconRotate: Float? = null): ItemIcon = FabIconImpl(iconRes = iconRes, iconRotate = iconRotate)