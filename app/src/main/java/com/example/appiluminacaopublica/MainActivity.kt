package com.example.appiluminacaopublica

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.example.appiluminacaopublica.ui.LoginFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

//        toolbar = findViewById(R.id.topToolbar)
//        bottomNav = findViewById(R.id.bottomNavigationView)
//        setSupportActionBar(toolbar)
//
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        navController = navHostFragment.navController

//        setupActionBarWithNavController(navController)

//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            toolbar.title = when (destination.id) {
//                R.id.feedFragment -> "Feed"
//                R.id.mapaFragment -> "Mapa"
//                R.id.chamadoFragment -> "Chamados"
//                R.id.loginFragment -> "Login"
//                R.id.registerFragment -> "Cadastro"
//                else -> "App"
//            }
//
//            bottomNav.visibility = when (destination.id) {
//                R.id.feedFragment, R.id.mapaFragment, R.id.chamadoFragment -> View.VISIBLE
//                else -> View.GONE
//            }
//        }
//
//        bottomNav.setupWithNavController(navController)
    }
}