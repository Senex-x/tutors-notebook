package com.example.tutorsnotebook.database

import android.util.Log
import com.example.tutorsnotebook.entities.Student
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.database
import io.github.serpro69.kfaker.*
import kotlin.random.Random

object Database {
    var database: DatabaseReference = Firebase.database.reference
    //TODO: refactor...
    //TODO: foolproof (???)

    init {
    }

    fun writeNewStudent(student: Student) {
        database.child("students").child(student.key).setValue(student)
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

    fun deleteAllStudents() {
        getAllStudentKeys {
            for (key in it) {
                database.child("students").child(key).removeValue()
            }
        }
    }

    fun addRandomUsers() {
        val faker = Faker()
        for (i in 0..10) {
            //TODO: mb add russian numbers
            writeNewStudent(
                Student(
                    key = Student.generateKey(setOf()),
                    name = faker.name.firstName(),
                    surname = faker.name.lastName(),
                    studentPhone = faker.phoneNumber.cellPhone().replace("[^0-9]".toRegex(), "")
                        .toLong(),
                    parentName = faker.name.firstName(),
                    parentMiddleName = faker.name.lastName(),
                    parentPhone = faker.phoneNumber.cellPhone().replace("[^0-9]".toRegex(), "")
                        .toLong(),
                    avgScore = (0..100).random(),
                    isPayed = Random.nextBoolean(),
                    scoreStatus = Student.ScoreStatus.values().random()
                )
            )
        }
    }
}