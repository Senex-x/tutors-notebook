package com.example.tutorsnotebook.entities

import java.net.URI

class Homework(
    var author: Student,
    var score: Int,
    var status: Boolean,
    var images: MutableList<URI>
) {
}