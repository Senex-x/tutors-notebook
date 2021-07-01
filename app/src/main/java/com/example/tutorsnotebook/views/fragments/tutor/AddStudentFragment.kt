package com.example.tutorsnotebook.views.fragments.tutor

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

import com.example.tutorsnotebook.R
import com.example.tutorsnotebook.database.Database
import com.example.tutorsnotebook.entities.Student
import kotlinx.android.synthetic.main.fragment_add_student.*
import kotlinx.android.synthetic.main.fragment_add_student.generate_login_button
import kotlinx.android.synthetic.main.reg_temp.*

class AddStudentFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_add_student, container, false)

        initGenerateBtn(rootView)
        initRegisterBtn(rootView)

        return rootView
    }

    private fun initGenerateBtn(rootView: View) {
        var btn: Button = rootView.findViewById(R.id.generate_login_button)
        btn.setOnClickListener() {
            Database.getAllStudentKeys {
                generate_login_button.text = Student.generateKey(it)
                btn.isClickable = false
            }
        }
    }

    //add: Check Fields
    //TODO: Refresh recycler
    private fun initRegisterBtn(rootView: View) {
        var btn: Button = rootView.findViewById(R.id.register_button)
        btn.setOnClickListener() {
            var fields = setOf<EditText>(
                rootView.findViewById<EditText>(R.id.student_name_field),
                rootView.findViewById<EditText>(R.id.student_surname_field),
                rootView.findViewById<EditText>(R.id.student_number_field),
                rootView.findViewById<EditText>(R.id.parent_number_field)
            )

            var someFiledsAreEmpty = false

            for (field in fields) {
                if (TextUtils.isEmpty(field.text.toString())) {
                    someFiledsAreEmpty = true
                }
            }

            if (someFiledsAreEmpty) {
                Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
            } else {
                var student = Student(
                    key = rootView.findViewById<Button>(R.id.generate_login_button).text.toString(),
                    name = rootView.findViewById<EditText>(R.id.student_name_field).text.toString(),
                    surname = rootView.findViewById<EditText>(R.id.student_surname_field).text.toString(),
                    studentPhone = rootView.findViewById<EditText>(R.id.student_number_field).text.toString()
                        .toInt(), //TODO: add:regex
                    parentPhone = rootView.findViewById<EditText>(R.id.parent_number_field).text.toString()
                        .toInt()
                )

                Database.writeNewStudent(student)

                Navigation.findNavController(it)
                    .navigate(R.id.action_addStudentFragment_to_studentsFragment)
            }


            //TODO: reset recycler
        }


    }
}