package com.example.tutorsnotebook.entities

data class Note(
    var id:Int,
    var title:String,
    var dateTime:String,
    var noteText:String,
)
{
    fun toTSV(): String = "" +
            "$id\t" +
            "$title\t" +
            "$dateTime\t" +
            "$noteText"
}