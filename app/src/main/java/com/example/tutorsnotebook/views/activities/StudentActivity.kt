package com.example.tutorsnotebook.views.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.tutorsnotebook.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class StudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)

        // Setting up navigation host for Jetpack's Navigation component framework
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.student_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Setting up navigation view for Jetpack's Navigation component framework
        val bottomNavigationView: BottomNavigationView =
            findViewById(R.id.student_bottom_navigation_view)
        bottomNavigationView.setupWithNavController(navController)
    }
}