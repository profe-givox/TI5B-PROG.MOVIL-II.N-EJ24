package net.ivanvega.milocalizacionymapasb.ui.mapas

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.utsman.osmandcompose.OpenStreetMap
import com.utsman.osmandcompose.Marker as MarkerOSMDC
import com.utsman.osmandcompose.Polyline as PolylineOSMDC
import com.utsman.osmandcompose.rememberCameraState
import com.utsman.osmandcompose.rememberMarkerState
import org.osmdroid.util.GeoPoint

@Composable
fun MiPrimerMapa() {
    val singapore = LatLng(20.14025, -101.15112)

    val polyline = listOf(   LatLng(20.14025, -101.15112),  LatLng(20.14338, -101.14988),
        LatLng(20.14389, -101.15111))

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }

    var uiSettings by remember { mutableStateOf(MapUiSettings()) }
    var properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }

    Box(Modifier.fillMaxSize()) {

        GoogleMap(
            modifier = Modifier.matchParentSize(),
            properties = properties,
            uiSettings = uiSettings,
            cameraPositionState = cameraPositionState
        ) {
            Polyline(points = polyline, color = Color.Red)
            Marker(
                state = MarkerState(position = singapore),
                title = "Mexico City",
                snippet = "Marker in Angel de la independencia"
            )
        }

        Switch(
            checked = uiSettings.zoomControlsEnabled,
            onCheckedChange = {
                uiSettings = uiSettings.copy(zoomControlsEnabled = it)
            }
        )
    }
}

@Composable
fun MiMapaOSMDroidCompose() {
    // define camera state
    val cameraState = rememberCameraState {
        geoPoint = GeoPoint(20.14025, -101.15112)
        zoom = 18.0 // optional, default is 5.0
    }

    // define marker state
    val depokMarkerState = rememberMarkerState(
        geoPoint = cameraState.geoPoint
    )

    // define polyline
    val geoPoint = remember {
        listOf( GeoPoint(20.14025, -101.15112),  GeoPoint(20.14338, -101.14988),
            GeoPoint(20.14389, -101.15111))
    }

    // add node
    OpenStreetMap(
        modifier = Modifier.fillMaxSize(),
        cameraState = cameraState
    ){
        // add marker here
        MarkerOSMDC(
            state = depokMarkerState  // add marker state
        )
        // add polyline
        PolylineOSMDC(geoPoints = geoPoint)
    }
}