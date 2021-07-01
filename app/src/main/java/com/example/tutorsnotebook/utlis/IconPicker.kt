package com.example.tutorsnotebook.utlis

import com.example.tutorsnotebook.R

object IconPicker {
    val icons = arrayOf(
        R.drawable.ic_1_quiz,
        R.drawable.ic_2_quiz,
        R.drawable.ic_3_quiz,
        R.drawable.ic_4_quiz,
        R.drawable.ic_5_quiz,
        R.drawable.ic_6_quiz
    )
    var currentIcon = 0

    fun getIcon(): Int {
        currentIcon = (currentIcon+ 1) % icons.size
        return icons[currentIcon]
    }
}