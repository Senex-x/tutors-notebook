package com.example.tutorsnotebook.entities

class Student(
    var key: String,
    var name: String,
    var surname: String,
    var studentPhone: Int,
    var parentPhone: Int,
    var isPayed: Boolean = false,
    var avgScore: Int = 0,
    // to update average score correctly
    var scoreCounter: Int = 0,
    // to let app know if score is currently ascending or not
    var scoreStatus: ScoreStatus = ScoreStatus.STAYS
) {
    // sample demonstration of adding a score of a new student's mark
    fun addScore(newScore: Int) {
        avgScore = (avgScore * scoreCounter + newScore) / ++scoreCounter
    }

    enum class ScoreStatus {
        INCREASES,
        STAYS,
        DECREASES
    }
}