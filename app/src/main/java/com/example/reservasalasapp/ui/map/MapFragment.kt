package com.example.reservasalasapp.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.reservasalasapp.R
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.locationcomponent.location

class MapFragment : Fragment() {

    private lateinit var mapView: MapView
    private lateinit var pointAnnotationManager: PointAnnotationManager

    // Lanzador de permisos
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) enableBluePulse()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mapView = MapView(requireContext())

        // Configurar cámara inicial (Quito)
        mapView.mapboxMap.setCamera(
            CameraOptions.Builder()
                .center(Point.fromLngLat(-78.52495, -0.22985))
                .zoom(9.0)
                .build()
        )

        // Crear administrador de marcadores
        pointAnnotationManager = mapView.annotations.createPointAnnotationManager()

        // 🔹 Definir varias zonas (coordenadas quemadas)
        val zones = listOf(
            Point.fromLngLat(-78.513284, -0.219463), // Zona 1
            Point.fromLngLat(-78.508456, -0.215086), // Zona 2
            Point.fromLngLat(-78.518410,  -0.229089),
            Point.fromLngLat(-78.512405,  -0.219753) // Zona 3
        )

        // Agregar marcadores para cada zona
        zones.forEach { addMarker(it) }

        // Pedir permisos de ubicación
        checkLocationPermission()

        // ✅ 🔹 NUEVO: Detectar clic en marcador y navegar a reservaciones
        pointAnnotationManager.addClickListener {
            val navController = findNavController()
            navController.navigate(R.id.nav_slideshow) // ID del fragment de reservaciones
            true // indica que el clic fue manejado
        }

        return mapView
    }

    // ✅ Verificar permisos
    private fun checkLocationPermission() {
        when {
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> enableBluePulse()
            else -> requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    // 🔹 Habilitar punto azul pulsante
    private fun enableBluePulse() {
        mapView.location.updateSettings {
            enabled = true
            pulsingEnabled = true
        }
    }

    // 🔴 Función para agregar marcador
    private fun addMarker(point: Point) {
        val drawable = requireContext().getDrawable(R.drawable.placeholder)!!
        val bitmap = if (drawable is BitmapDrawable) {
            drawable.bitmap
        } else {
            val bmp = android.graphics.Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                android.graphics.Bitmap.Config.ARGB_8888
            )
            val canvas = android.graphics.Canvas(bmp)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bmp
        }

        pointAnnotationManager.create(
            PointAnnotationOptions()
                .withPoint(point)
                .withIconImage(bitmap)
                .withIconSize(0.25)
        )
    }

    override fun onStart() { super.onStart(); mapView.onStart() }
    override fun onStop() { super.onStop(); mapView.onStop() }
    override fun onLowMemory() { super.onLowMemory(); mapView.onLowMemory() }
    override fun onDestroyView() { super.onDestroyView(); mapView.onDestroy() }
}
