package com.example.tutorsnotebook.database

import android.util.Log
import com.example.tutorsnotebook.entities.Homework
import com.example.tutorsnotebook.entities.Student
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.database
import java.util.ArrayList

object Database {
    var database: DatabaseReference = Firebase.database.reference
    //TODO: refactor...
    //TODO: foolproof (???)

    init {
    }

    fun writeNewStudent(student: Student) {
        getAllStudentKeys {
            if (student.key in it) {
                student.key = (1000..9999).random().toString()
                writeNewStudent(student)
            } else {
                database.child("students").child(student.key).setValue(student)
            }
        }
    }

    fun getStudent(key: String, callback: (Student) -> Unit) {
        database.child("students").child(key).get().addOnSuccessListener {
            if (it.exists()) {
                callback.invoke(it.getValue(Student::class.java)!!)
            }
        }
    }

    fun getAllStudents(callback: (List<Student>) -> Unit) {
        database.child("students").get().addOnSuccessListener {
            if (it.exists()) {
                var result = mutableListOf<Student>()
                for (child in it.children) {
                    result.add(child.getValue(Student::class.java)!!)
                }
                callback.invoke(result)
            }
        }
    }

    fun getAllStudentKeys(callback: (Set<String>) -> Unit) {
        var keys = mutableSetOf<String>()
        getAllStudents() { result ->
            if (result.size > 0) {
                for (student in result) {
                    keys.add(student.key)
                }
                callback.invoke(keys)
            }
        }
    }

    fun putHomework(studentKey: String, images: ArrayList<String>) {
        TODO("Not yet implemented")
    }

    fun putHomeworkScore(studentKey: String, score: Int) {
        TODO("Not yet implemented")
    }

    fun getHomeworkFromDatabase(studentKey: String): Homework {
        TODO("Not yet implemented")
    }


}