package com.example.appiluminacaopublica.ui.Feed.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.appiluminacaopublica.R
//import com.example.appiluminacaopublica.ui.Feed.Chamados.ChamadosFragment
import com.example.appiluminacaopublica.ui.Feed.FeedFragment
import com.example.appiluminacaopublica.ui.Feed.main.mapa.MapaFragment
//import com.example.appiluminacaopublica.ui.Feed.main.mapa.MapaFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

//class MainActivity : AppCompatActivity() {
//
//    private lateinit var bottomNavigation: BottomNavigationView
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main_feed)
//
//        bottomNavigation = findViewById(R.id.bottomNavigation)
//
//        loadFragment(FeedFragment()) // Tela inicial
//
//        bottomNavigation.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.menu_feed -> {
//                    loadFragment(FeedFragment())
//                    true
//                }
//                R.id.menu_map -> {
//                    loadFragment(MapaFragment())
//                    true
//                }
//                R.id.nav_chamados -> {
//                    loadFragment(ChamadosFragment())
//                    true
//               }
//                else -> false
//            }
//        }
//       }

//    private fun loadFragment(fragment: Fragment) {
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fragmentContainer, fragment)
//            .commit()
//    }
//}