package com.example.tutorsnotebook.views.fragments.tutor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.tutorsnotebook.R
import com.example.tutorsnotebook.entities.Note
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class CreateNoteFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_create_note, container, false)
        initButtons(rootView)
        return rootView;
    }

    private fun initButtons(rootView : View) {
        val backButton : ImageView = rootView.findViewById(R.id.button_back)
        val saveButton : ImageView = rootView.findViewById(R.id.button_done)
        backButton.setOnClickListener { view ->
            Navigation.findNavController(view)
                .navigate(R.id.action_createNoteFragment_to_notesFragment)
        }
        saveButton.setOnClickListener { view ->
            saveNote(rootView)
            Navigation.findNavController(view)
                .navigate(R.id.action_createNoteFragment_to_notesFragment)
        }
    }

    private fun saveNote(rootView: View) {
        val noteId = Random().nextInt(9999 + 1)
        val noteTitleTextField : EditText = rootView.findViewById(R.id.note_title)
        val noteDate = SimpleDateFormat("dd/MM/yy HH/mm/ss", Locale.UK).format(Date()).toString()
        val noteContentTextField : EditText = rootView.findViewById(R.id.note_content)
        var note = Note(
            noteId,
            noteTitleTextField.text.toString(),
            noteDate,
            noteContentTextField.text.toString()
        )
        Snackbar.make(rootView, note.toString(), Snackbar.LENGTH_LONG).show()
    }

}