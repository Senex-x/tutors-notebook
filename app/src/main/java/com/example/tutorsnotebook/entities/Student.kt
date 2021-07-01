package com.example.tutorsnotebook.entities

//TODO: имя и фамилия с большой буквы
class Student(
    var key: String = "key",
    var name: String = "name",
    var surname: String = "surname",
    var studentPhone: Long = 0,
    var parentName: String = "parent name",
    var parentPhone: Long = 0,
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

    override fun toString(): String {
        return "Student(key='$key', " +
                "name='$name', " +
                "surname='$surname', " +
                "studentPhone=$studentPhone, " +
                "parentName='$parentName', " +
                "parentPhone=$parentPhone, " +
                "isPayed=$isPayed, " +
                "avgScore=$avgScore, " +
                "scoreCounter=$scoreCounter, " +
                "scoreStatus=$scoreStatus)"
    }

    companion object {
        fun generateKey(keys: Set<String>): String {
            var key = (1000..9999).random().toString()
            while (key in keys) {
                key = (1000..9999).random().toString()
            }
            return key
        }
    }

    enum class ScoreStatus {
        INCREASES,
        STAYS,
        DECREASES
    }
}