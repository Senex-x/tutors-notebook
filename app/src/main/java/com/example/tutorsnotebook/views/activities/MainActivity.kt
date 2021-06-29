package com.example.tutorsnotebook.views.activities

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.tutorsnotebook.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Disabling night-marked themes
        if (Build.VERSION.SDK_INT >= 29) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        // Setting up navigation host for Jetpack's Navigation component framework
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Setting up navigation view for Jetpack's Navigation component framework
        val bottomNavigationView: BottomNavigationView =
            findViewById(R.id.main_bottom_navigation_view)

        bottomNavigationView.setupWithNavController(navController)
    }

    fun setupNavigationController() {

    }
}