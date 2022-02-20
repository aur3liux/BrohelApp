package com.aur3liux.brohelapp.view.components.floatingmenu

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ItemFloatingActionButton(
    modifier: Modifier = Modifier,
    items: List<ItemData>,
    fabState: MutableState<ItemState> = rememberMultiFabState(),
    fabIcon: ItemIcon,
    fabOption: ItemOption = FabOption(),
    onFabItemClicked: (fabItem: ItemData) -> Unit,
    stateChange: (fabState: ItemState)-> Unit = {}
){
    val rotation by animateFloatAsState(
        if (fabState.value == ItemState.EXPAND){
            fabIcon.iconRotate ?: 0f
        }else {
            0f
        }
    )

    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.End) {
        AnimatedVisibility(
            visible = fabState.value.isExpanded(),
            enter = fadeIn() + expandVertically(),
            exit = fadeOut()) {
                LazyColumn(modifier = Modifier.wrapContentSize(),
                    horizontalAlignment =  Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(15.dp)){
                        items(items.size){ index ->
                            MiniFabItem(item = items[index],
                                fabOptions = fabOption,
                                onFabItemClicked = onFabItemClicked)
                        }
                    item{}
                }//LazyColumn
        }//AnimateVisivility

        FloatingActionButton(
            onClick = {
                fabState.value= fabState.value.toggleValue()
                stateChange(fabState.value)
            },
            backgroundColor = fabOption.backgroundTint,
            contentColor = fabOption.iconTint) {
                Icon(
                    painter = painterResource(id = fabIcon.iconRes),
                    contentDescription = "",
                    modifier = Modifier.rotate(rotation),
                    tint = fabOption.iconTint)
        }
    }
}

@Composable
fun MiniFabItem(
    item: ItemData,
    fabOptions: ItemOption,
    onFabItemClicked: (item: ItemData)-> Unit
) {
    Row(modifier= Modifier
        .wrapContentSize()
        .padding(end = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically){
        if(fabOptions.showLabel){
            Text(text = item.label,
                color = Color.Blue,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(Color.Transparent) //Color de fondo del texto flotante
                    .padding(horizontal = 6.dp, vertical = 4.dp)
            )
        }

        FloatingActionButton(
            onClick = {
                onFabItemClicked(item) },
            modifier = Modifier.size(40.dp),
            backgroundColor = fabOptions.backgroundTint,
            contentColor = fabOptions.iconTint) {
                Icon(painter = painterResource(id = item.iconRes),
                    contentDescription = "",
                    tint = fabOptions.iconTint)
                }
        }
}