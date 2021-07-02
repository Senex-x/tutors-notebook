package com.example.tutorsnotebook.database

import com.example.tutorsnotebook.entities.Homework
import com.example.tutorsnotebook.entities.Student
import com.example.tutorsnotebook.utils.OnItemGetListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database

import com.google.firebase.ktx.Firebase
import io.github.serpro69.kfaker.Faker

import kotlin.random.Random


object Database {
    var database: DatabaseReference = Firebase.database.reference

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

    fun putStudent(student: Student) {
        database.child("students").child(student.key).setValue(student)
    }

    // Deliberately overriding homeworks
    fun putHomework(homework: Homework) {
        database.child("homeworks").child(homework.key).setValue(homework)
    }

    fun putHomeworkScore(studentKey: String, score: Int) {
        database.child("homeworks").child(studentKey).child("score").setValue(score)
    }

    fun getHomework(studentKey: String, listener: OnItemGetListener<Homework>) {
        database.child("homeworks").child(studentKey).get().addOnSuccessListener {
            if (it.exists()) {
                listener.onSuccess(it.getValue(Homework::class.java)!!)
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

    fun getHomeworkScore(studentKey: String, listener: OnItemGetListener<Int>) {
        database.child("homeworks").child(studentKey).child("score").get()
            .addOnSuccessListener {
                if (it.exists()) {
                    listener.onSuccess(it.getValue(Int::class.java)!!)
                }
            }
    }

    fun getStudentAverageScore(studentKey: String, listener: OnItemGetListener<Int>) {
        database.child("students").child(studentKey).child("avgScore").get()
            .addOnSuccessListener {
                if (it.exists()) {
                    listener.onSuccess(it.getValue(Int::class.java)!!)
                }
            }
    }

    fun addRandomUsers() {
        val faker = Faker()
        for (i in 0..10) {
            writeNewStudent(
                Student(
                    key = Student.generateKey(setOf()),
                    name = faker.name.firstName(),
                    surname = faker.name.lastName(),
                    studentPhone = faker.phoneNumber.cellPhone()
                        .replace("[^0-9]".toRegex(), "")
                        .toLong(),
                    parentName = faker.name.firstName(),
                    parentMiddleName = faker.name.lastName(),
                    parentPhone = faker.phoneNumber.cellPhone()
                        .replace("[^0-9]".toRegex(), "")
                        .toLong(),
                    avgScore = (0..100).random(),
                    isPayed = Random.nextBoolean(),
                    scoreStatus = Student.ScoreStatus.values().random()
                )
            )
        }
    }
}

