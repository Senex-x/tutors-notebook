package com.example.tutorsnotebook.views.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.tutorsnotebook.R
import com.example.tutorsnotebook.database.Database
import com.example.tutorsnotebook.entities.Student
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.activity_auth.view.*

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        // Disabling night-marked themes
        if (Build.VERSION.SDK_INT >= 29) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        initAuthButton()
        initTutorButton()
        initStudentButton()
    }


    //TODO: String to res
    private fun initAuthButton() {
        card_view.auth_button_enter.setOnClickListener {
            Database.getAllStudentKeys {
                if (card_view.auth_edit_text_password.text.toString() == "0000") {
                    Toast.makeText(this, "SUCCESS TEACHER", Toast.LENGTH_LONG).show() //debug
                    startActivity(Intent(this, MainActivity::class.java))
                } else if (card_view.auth_edit_text_password.text.toString() in it) {
                    Toast.makeText(this, "SUCCESS STUDENT", Toast.LENGTH_LONG).show() //debug
                    val intent = Intent(this, StudentActivity::class.java).apply {
                        putExtra("key", card_view.auth_edit_text_password.text.toString())
                    }
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this,
                        "Пользователь не найден, попробуйте ещё раз",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
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