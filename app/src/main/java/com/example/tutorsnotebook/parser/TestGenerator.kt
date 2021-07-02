package com.example.tutorsnotebook.parser

import com.aspose.cells.Workbook
import java.io.InputStream

import com.example.tutorsnotebook.entities.Question

//To TEST: TestGenerator.generateQuestions(applicationContext.resources.openRawResource(R.raw.test)) to MainActivity
class TestGenerator {
    companion object {
        private const val LAST_QUESTION_ROW_COUNT = 51

        fun generateQuestions(stream: InputStream): MutableList<Question> {
            var cells = Workbook(stream).worksheets[0].cells
            var questions = mutableListOf<Question>()

            for (i in 1..LAST_QUESTION_ROW_COUNT) {
                var question =
                    Question(
                        problem = cells.get(i, 1).getStringValue(),
                        answers = mutableListOf<String>("s"),
                        rightAnswer = 0
                    )
                for (j in 1..cells.rows[i].lastCell.column) {
                    question.answers.add(cells.get(i, j).getStringValue())
                }
                questions.add(question)
            }
            return questions
        }
    }
}