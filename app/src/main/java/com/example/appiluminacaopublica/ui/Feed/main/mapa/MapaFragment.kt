package com.example.appiluminacaopublica.ui.Feed.main.mapa
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import com.example.appiluminacaopublica.R
import com.example.appiluminacaopublica.data.model.Chamado
//import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.getSourceAs
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.viewport
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.plugin.gestures.gestures
//import com.mapbox.maps.extension.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.extension.style.layers.properties.generated.TextAnchor
import com.mapbox.maps.extension.style.layers.properties.generated.TextJustify
import com.mapbox.maps.extension.style.layers.properties.generated.TextTransform
import com.mapbox.maps.extension.style.layers.properties.generated.Visibility
//import com.mapbox.maps.extension.style.layers.properties.generated.textField
//import com.mapbox.maps.extension.style.layers.properties.generated.textSize
//import com.mapbox.maps.extension.style.layers.properties.generated.textAllowOverlap
//import com.mapbox.maps.extension.style.layers.properties.generated.textIgnorePlacement

class MapaFragment : Fragment() {
    private lateinit var mapView: MapView
    private val db = Firebase.firestore

    private var imagemAdicionada = false

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            ativarLocalizacao()
        } else {
            Toast.makeText(context, "Permissão negada", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_mapa, container, false)
        mapView = view.findViewById(R.id.mapView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS) { style ->
            checarLocalizacao()
//            carregarChamadosNoMapa(style)

//            with(mapView) {
//                location.locationPuck = createDefault2DPuck(withBearing = true)
//                location.enabled = true
//                location.puckBearing = PuckBearing.COURSE
//                location.puckBearingEnabled = true
//                viewport.transitionTo(
//                    targetState = viewport.makeFollowPuckViewportState(),
//                    transition = viewport.makeImmediateViewportTransition()
//                )
//}

            checarLocalizacao()
//            val cameraOptions = CameraOptions.Builder()
//                .center(Point.fromLngLat(-50.025281, -29.763185)) // longitude, latitude
//                .zoom(14.0)
//                .build()
//
//            mapView.getMapboxMap().setCamera(cameraOptions)
        }
    }

        private fun checarLocalizacao() {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                ativarLocalizacao()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }

        private fun ativarLocalizacao() {
            val locationComponentPlugin = mapView.location

            locationComponentPlugin.updateSettings {
                enabled = true
                pulsingEnabled = true // círculo azul
            }

            val viewportPlugin = mapView.viewport
            val followPuckViewportState = viewportPlugin.makeFollowPuckViewportState()
            val transition = viewportPlugin.makeImmediateViewportTransition()
            viewportPlugin.transitionTo(followPuckViewportState, transition)

    }

//    private fun carregarChamadosNoMapa(style: Style) {
//        db.collection("chamados")
//            .get()
//            .addOnSuccessListener { documents ->
//                val features = mutableListOf<com.mapbox.geojson.Feature>()
//
//                documents.forEach { doc ->
//                    val latString = doc.getString("latitude")
//                    val lonString = doc.getString("longitude")
//
//                    if (!latString.isNullOrEmpty() && !lonString.isNullOrEmpty()) {
//                        val lat = latString.toDoubleOrNull()
//                        val lon = lonString.toDoubleOrNull()
//                        if (lat != null && lon != null) {
//                            val point = Point.fromLngLat(lon, lat)
//                            features.add(com.mapbox.geojson.Feature.fromGeometry(point))
//                        }
//                    }
//                }
//
//                if (features.isEmpty()) return@addOnSuccessListener
//
//                val geoJsonSource = style.getSourceAs<GeoJsonSource>("marker-source")
//
//                if (geoJsonSource == null) {
//                    // Cria a fonte e camada se não existirem
//                    val source = GeoJsonSource.Builder("marker-source")
//                        .featureCollection(FeatureCollection.fromFeatures(features))
//                        .build()
//                    style.addSource(source)
//
//                    val symbolLayer = SymbolLayer("marker-layer", "marker-source")
//                        .withProperties(
//                            textField("📍"),
//                            textSize(20.0),
//                            textAllowOverlap(true),
//                            textIgnorePlacement(true)
//                        )
//                    style.addLayer(symbolLayer)
//                } else {
//                    // Atualiza a fonte existente com novos pontos
//                    geoJsonSource.featureCollection(FeatureCollection.fromFeatures(features))
//                }
//            }
//            .addOnFailureListener {
//                // trate falha da consulta firestore
//            }
//    }
}
