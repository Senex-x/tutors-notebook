package com.example.tutorsnotebook.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.tutorsnotebook.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setting up navigation host for Jetpack's Navigation component framework
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Setting up navigation view for Jetpack's Navigation component framework
        val bottomNavigationView: BottomNavigationView =
            findViewById(R.id.main_bottom_navigation_view)
        bottomNavigationView.setupWithNavController(navController)
    }
}