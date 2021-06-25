package com.example.tutorsnotebook.entity

import java.util.*

class Test(
    var theme: String,
    var questions: MutableList<Question>,
    var destination: Student,
    var score: Int,
    var timer: Timer,
    var status: Boolean
) {
    override fun toString(): String {
        return theme + " " + questions.toString() + " " + destination + " " + score + " " + timer + " " + status
    }
}