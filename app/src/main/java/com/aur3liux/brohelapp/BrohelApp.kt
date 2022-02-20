package com.aur3liux.brohelapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import com.aur3liux.brohelapp.ui.theme.BottomSheetShape
import com.aur3liux.brohelapp.view.Cuenta
import com.aur3liux.brohelapp.view.components.BottomSheetWithCloseDialog
import com.aur3liux.brohelapp.view.components.Configuracion
import com.aur3liux.brohelapp.view.components.InfoLegal
import com.aur3liux.brohelapp.view.components.floatingmenu.FabIcon
import com.aur3liux.brohelapp.view.components.floatingmenu.FabOption
import com.aur3liux.brohelapp.view.components.floatingmenu.ItemData
import com.aur3liux.brohelapp.view.components.floatingmenu.ItemFloatingActionButton
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch
import java.lang.IllegalStateException


sealed class BottomSheetScreen() {
    object Cuenta: BottomSheetScreen()
    object Configuracion: BottomSheetScreen()
    class InfoLegal(val argument:String):BottomSheetScreen()
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BrohelApp() {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    var currentBottomSheet: BottomSheetScreen? by remember{
        mutableStateOf(null)
    }

    if(scaffoldState.bottomSheetState.isCollapsed)
        currentBottomSheet = null

    val openSheet: (BottomSheetScreen) -> Unit = {
        scope.launch {
            currentBottomSheet = it
            scaffoldState.bottomSheetState.expand() }
    }

    val closeSheet: () -> Unit = {
        scope.launch {
            scaffoldState.bottomSheetState.collapse()
        }
    }

    BottomSheetScaffold(sheetPeekHeight = 0.dp, scaffoldState = scaffoldState,
        sheetShape = BottomSheetShape,
        sheetContent = {
            currentBottomSheet?.let { currentSheet ->
                SheetLayout(currentSheet,closeSheet)
            }
        }) { paddingValues ->

        Scaffold(
            isFloatingActionButtonDocked = true,
            floatingActionButton = {
                ItemFloatingActionButton(
                    items = listOf(
                        ItemData(
                            id = 1,
                            iconRes = R.drawable.ic_biker,
                            label = "Perfil"
                        ),
                        ItemData(
                            id = 2,
                            iconRes = R.drawable.ic_config,
                            label = "Configuración"
                        ),
                        ItemData(
                            id = 3,
                            iconRes = R.drawable.ic_legal,
                            label = "Información legal"
                        )
                    ),
                    fabIcon = FabIcon(
                        iconRes = R.drawable.ic_menu,
                        iconRotate = 45f),
                    onFabItemClicked = {
                        when(it.id){
                            1->{
                                openSheet(BottomSheetScreen.Cuenta)
                            }
                            2->{
                                openSheet(BottomSheetScreen.Configuracion)
                            }
                            3->{
                                openSheet(BottomSheetScreen.InfoLegal("Ok aqui hay algo"))
                            }
                        }
                        Log.i("CLICK", it.label)
                    },
                    fabOption = FabOption(
                        iconTint = Color.Blue,
                        showLabel = true))
            },
            floatingActionButtonPosition = FabPosition.End
        ){

            val context = LocalContext.current
            Box(
                modifier = Modifier.fillMaxSize()) {
                ShowMap(context = context) {
                }//Box
            }
        }//Scaffold

    }//BottomSheet


}//BrohelApp


@Composable
fun ShowMap(
    context: Context,
    onReady:(GoogleMap) ->Unit){

    val mapView = remember{ MapView(context) }
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    lifecycle.addObserver(rememberMapLifecycle(map = mapView))

    val origen = LatLng(19.8461031,-90.5386858) //Catedral CAMPECHE

    AndroidView(
        factory = {
            mapView.apply {
                mapView.getMapAsync{ googleMap ->
                    val zoomLevel = 15f

                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origen,zoomLevel))
                    googleMap.addMarker(
                        MarkerOptions()
                            .position(origen).title("Inicio")
                            .snippet("Aqui inicio")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.motorcycle_pin)))

                    googleMap.setOnMapClickListener { latlng ->

                    }
                    onReady(googleMap)
                }
            }
        })

}

@Composable
fun rememberMapLifecycle(map: MapView): LifecycleObserver {
    return remember {
        LifecycleEventObserver{ source, event ->
            when(event){
                Lifecycle.Event.ON_CREATE -> {
                    Log.i("CREATE", "CREATE")
                    map.onCreate(Bundle())
                }
                Lifecycle.Event.ON_START -> {
                    Log.i("START", "START")
                    map.onStart()}
                Lifecycle.Event.ON_RESUME ->{
                    map.onResume()
                    Log.i("RESUME", "RESUME")}
                Lifecycle.Event.ON_PAUSE ->{
                    Log.i("PAUSE", "PAUSE")
                    map.onPause()}
                Lifecycle.Event.ON_STOP -> {
                    Log.i("STOP", "STOP")
                    map.onStop()}
                Lifecycle.Event.ON_DESTROY -> map.onDestroy()
                Lifecycle.Event.ON_ANY -> throw IllegalStateException()
            }
        }
    }
}

@Composable
fun SheetLayout(currentScreen: BottomSheetScreen,onCloseBottomSheet :()->Unit) {
    BottomSheetWithCloseDialog(onCloseBottomSheet){
        when(currentScreen){
            BottomSheetScreen.Cuenta -> Cuenta()
            BottomSheetScreen.Configuracion -> Configuracion()
            is BottomSheetScreen.InfoLegal -> InfoLegal(argument = currentScreen.argument)
        }

    }
}