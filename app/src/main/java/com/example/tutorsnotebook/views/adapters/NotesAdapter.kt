package com.example.tutorsnotebook.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tutorsnotebook.R
import com.example.tutorsnotebook.entities.Note

class NotesAdapter(
    private val notes: List<Note>,
) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    class NoteViewHolder(noteView: View) : RecyclerView.ViewHolder(noteView) {
        var noteTitle : TextView? = null
        var noteContent : TextView? = null
        var noteDatetime : TextView? = null

        init {
            noteTitle = noteView.findViewById(R.id.note_item_title)
            noteContent = noteView.findViewById(R.id.note_item_content)
            noteDatetime = noteView.findViewById(R.id.note_item_datetime)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val noteView = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(noteView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currNote = notes[position]

        holder.noteTitle?.text = currNote.title
        holder.noteDatetime?.text = currNote.dateTime
        holder.noteContent?.text = currNote.noteText
    }

    override fun getItemCount(): Int = notes.size

}