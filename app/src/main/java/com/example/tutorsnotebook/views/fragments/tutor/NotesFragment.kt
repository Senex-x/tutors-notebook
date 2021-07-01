package com.example.tutorsnotebook.views.fragments.tutor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.tutorsnotebook.R
import com.example.tutorsnotebook.utils.ImageHandler
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NotesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_notes, container, false)

        initFabAdd(rootView)

        return rootView;
    }

    private fun initFabAdd(rootView: View) {
        val fab: FloatingActionButton = rootView.findViewById(R.id.create_note_button)
        fab.setOnClickListener { view ->
            Navigation.findNavController(view)
                .navigate(R.id.action_notesFragment_to_createNoteFragment)
        }
        fab.setImageDrawable(
            ImageHandler.getColoredDrawable(
                requireContext(),
                R.drawable.ic_add_24,
                R.color.primary
            )
        )
    }
}