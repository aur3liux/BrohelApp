package com.aur3liux.brohelapp.view.components.floatingmenu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

sealed class ItemState {
    object COLLAPSED: ItemState()
    object EXPAND: ItemState()

    fun isExpanded() = this == EXPAND

    fun toggleValue() = if (isExpanded()){
        COLLAPSED
    } else{
        EXPAND
    }
}

@Composable
fun rememberMultiFabState() =
    remember{
    mutableStateOf<ItemState>(ItemState.COLLAPSED)
}