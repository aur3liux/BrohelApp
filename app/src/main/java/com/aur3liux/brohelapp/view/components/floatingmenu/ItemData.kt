package com.aur3liux.brohelapp.view.components.floatingmenu

import androidx.annotation.DrawableRes

data class ItemData(
    val id: Int,
    @DrawableRes val iconRes: Int,
    val label: String = ""
)
