package com.example.tutorsnotebook.entities

import java.net.URI

class Homework(
    var authorKey: Int,
    var score: Int = -1,
    var images: ArrayList<String>,
    var comment: String = ""
) {
}