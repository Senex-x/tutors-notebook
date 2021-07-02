package com.example.tutorsnotebook.utils

import android.content.Context
import com.example.tutorsnotebook.entities.Note
import java.io.File
import java.util.stream.Collectors

class NotesStorageHandler {

    companion object {
        const val NOTES_SOURCE_FILE_NAME : String = "notes.tsv"
    }

    fun readNotes(context : Context) : ArrayList<Note> {
        val textFromFile = FileHandler(context).getTextFromFile(NOTES_SOURCE_FILE_NAME)
        if (textFromFile == "") return ArrayList()
        val res = ArrayList<Note>()
        textFromFile.split("\n").stream().forEach { line ->
            if (line != "") {
                val args = line.split("\t")
                val id = Integer.parseInt(args[0])
                val title = args[1]
                val dateTime = args[2]
                val text = args[3]
                res.add(Note(id, title, dateTime, text))
            }
        }
        return res
    }

    fun writeNote(newNote: Note, context: Context) {
        // Overwrite existing note by the same id
        val notes = readNotes(context).stream()
            .collect(Collectors.toMap(Note::id, Note::toTSV))
        notes[newNote.id] = newNote.toTSV()

        val fileContent = notes.values.stream()
            .collect(Collectors.joining("\n"))
        FileHandler(context).saveTextToFile(NOTES_SOURCE_FILE_NAME, fileContent)
    }

    fun getNoteAmount(context: Context) = readNotes(context).size
}