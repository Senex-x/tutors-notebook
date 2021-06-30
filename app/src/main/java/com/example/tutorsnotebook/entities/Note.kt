package com.example.tutorsnotebook.entities

data class Note(
    var id:Int,
    var title:String,
    var dateTime:String,
    var noteText:String,
)
{
    override fun toString(): String = "$title : $dateTime"
}