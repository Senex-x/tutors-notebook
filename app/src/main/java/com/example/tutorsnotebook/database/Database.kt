package com.example.tutorsnotebook.database

import com.example.tutorsnotebook.entities.Homework
import com.example.tutorsnotebook.entities.Student
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


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
                val result = mutableListOf<Student>()
                for (child in it.children) {
                    result.add(child.getValue(Student::class.java)!!)
                }
                callback.invoke(result)
            }
        }
    }

    fun getAllStudentKeys(callback: (Set<String>) -> Unit) {
        val keys = mutableSetOf<String>()
        getAllStudents { result ->
            if (result.isNotEmpty()) {
                for (student in result) {
                    keys.add(student.key)
                }
                callback.invoke(keys)
            }
        }
    }

    // Deliberately overriding homeworks
    fun putHomework(homework: Homework) {
        database.child("homeworks").child(homework.key).setValue(homework)
    }

    fun putHomeworkScore(studentKey: String, score: Int) {
        database.child("homeworks").child(studentKey).child("score").setValue(score)
    }

    fun getHomework(studentKey: String, listener: OnDataGetListener) {
        database.child("homeworks").child(studentKey).get().addOnSuccessListener {
            if (it.exists()) {
                listener.onSuccess(it)
            }
        }
    }

    fun getHomeworkScore(studentKey: String, listener: OnDataGetListener) {
        database.child("homeworks").child(studentKey).child("score").get().addOnSuccessListener {
            if(it.exists()) {
                listener.onSuccess(it)
            }
        }
    }
}
