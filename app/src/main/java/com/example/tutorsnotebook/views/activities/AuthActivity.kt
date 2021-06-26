package com.example.tutorsnotebook.views.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.tutorsnotebook.R

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        // Disabling night-marked themes
        if(Build.VERSION.SDK_INT >= 29) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        initTutorButton()
        initStudentButton()
    }

    // TODO: rename
    private fun initTutorButton() {
        val tutorButton = findViewById<Button>(R.id.auth_button_tutor)
        tutorButton.setOnClickListener {
            val data = "optional data"
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("key", data)
            }
            startActivity(intent)
        }
    }

    // TODO: rename
    private fun initStudentButton() {
        val studentButton = findViewById<Button>(R.id.auth_button_student)
        studentButton.setOnClickListener {
            val data = "optional data"
            val intent = Intent(this, StudentActivity::class.java).apply {
                putExtra("key", data)
            }
            startActivity(intent)
        }
    }
}