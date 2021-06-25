package com.example.tutorsnotebook.entity

class Question(var problem: String, var answers: MutableList<String>, var rightAnswer: Int) {
    override fun toString(): String {
        return problem + " " + answers + " " + rightAnswer
    }
}