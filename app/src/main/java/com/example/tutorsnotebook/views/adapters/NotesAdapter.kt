package com.example.tutorsnotebook.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tutorsnotebook.R
import com.example.tutorsnotebook.entities.Note
import java.util.*


class NotesAdapter(
    var notes: List<Note>
) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currNote = notes[position]
        currNote.id = Random().nextInt()
        currNote.dateTime = Date().toString()
        currNote.title = "Salam"
        currNote.noteText = "Salam"
    }

    override fun getItemCount(): Int = notes.size

}