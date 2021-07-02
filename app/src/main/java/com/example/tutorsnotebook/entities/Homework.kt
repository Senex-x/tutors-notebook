package com.example.tutorsnotebook.entities

import java.net.URI

class Homework(
    var key: String = "-1",
    var score: Int = -1,
    var images: ArrayList<String> = ArrayList(),
    var message: String = ""
) {
    override fun toString(): String {
        return "Homework(key='$key', score=$score, images=$images, message='$message')"
    }
}