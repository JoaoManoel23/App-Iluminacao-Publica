package com.example.appiluminacaopublica.ui

import android.os.Bundle
import android.view.*
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.appiluminacaopublica.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.home_nav_host) as NavHostFragment

        val navController = navHostFragment.navController

        val bottomNav = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)
//        bottomNav.setupWithNavController(navController)

        bottomNav.setOnItemSelectedListener{item ->
            parentFragmentManager.fragments.forEach { fragment ->
                if (fragment is NavHostFragment && fragment != navHostFragment) {
                    childFragmentManager.popBackStack()
                    parentFragmentManager.popBackStack()
                }
            }

            NavigationUI.onNavDestinationSelected(item, navController)
            true
        }

        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.top_toolbar)
//        NavigationUI.setupWithNavController(toolbar,navController)
        toolbar.title= ""

        toolbar.inflateMenu(R.menu.top_app_bar_menu)
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_perfil -> {
                    val parentNavController = findNavController()
                    if (parentNavController.currentDestination?.id != R.id.perfilFragment) {
                        parentNavController.navigate(R.id.perfilFragment)
                    }
                    true
                }

                else -> false
            }
        }
}
}