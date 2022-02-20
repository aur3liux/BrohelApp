package com.aur3liux.brohelapp.view.components.floatingmenu

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import javax.annotation.concurrent.Immutable

@Immutable
interface ItemOption {
    @Stable val iconTint: Color
    @Stable val backgroundTint: Color
    @Stable val showLabel: Boolean
}

private class FabOptionsImpl(
    override val iconTint: Color,
    override val backgroundTint: Color,
    override val showLabel: Boolean
): ItemOption

@SuppressLint("ComposableNaming")
@Composable
fun FabOption(
    backgrountTint: Color = Color.White,
    iconTint: Color = Color.Black,
    showLabel: Boolean = false
): ItemOption = FabOptionsImpl(iconTint, backgrountTint, showLabel)