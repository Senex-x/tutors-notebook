package com.example.tutorsnotebook.entities

class Question(val problem: String, val answers: List<String>, val rightAnswer: Int) {
    override fun toString(): String {
        return "$problem $answers $rightAnswer"
    }
}