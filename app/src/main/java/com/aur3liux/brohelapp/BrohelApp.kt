package com.aur3liux.brohelapp

import android.content.Context
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import com.aur3liux.brohelapp.view.OpcionesCuenta
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
import java.lang.IllegalStateException

@Composable
fun BrohelApp() {
    var openMenuCuenta = remember { mutableStateOf(false)}

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
                     Log.i("CLICK", it.label)
                 },
                fabOption = FabOption(
                    iconTint = Color.Black,
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

        //Boton para acceder a la cuenta
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
            horizontalArrangement = Arrangement.End) {
            //Boton perfilk
            IconButton(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.primary)
                    .padding(10.dp),
                onClick = {
                    openMenuCuenta.value = true
                }) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    tint = MaterialTheme.colors.surface,
                    contentDescription = "")
            }//IconButton

            if(openMenuCuenta.value){
                OpcionesCuenta(onDismiss = {openMenuCuenta.value = false}){}
            }
        }//Row
    }//Scaffold
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

private fun getAddress(context: Context, lat: Double, lng: Double): String? {
    val geocoder = Geocoder(context)
    val list = geocoder.getFromLocation(lat,lng,1)
    return list[0].getAddressLine(0)
}