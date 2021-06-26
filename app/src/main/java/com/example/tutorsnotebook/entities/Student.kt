package com.example.tutorsnotebook.entities

class Student(
    var key: String,
    var name: String,
    var surname: String,
    var studentPhone: Int,
    var parentPhone: Int,
    var avgScore: Int = 0
)