package com.example.tutorsnotebook.views.activities

import android.content.res.Resources
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.tutorsnotebook.R
import com.example.tutorsnotebook.parser.TestGenerator
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.File
import java.io.FileInputStream

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Disabling night-marked themes
        if(Build.VERSION.SDK_INT >= 29) {
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
}