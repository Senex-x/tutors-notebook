package com.example.tutorsnotebook.views.fragments.tutor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tutorsnotebook.R
import com.example.tutorsnotebook.utils.ImageHandler
import com.example.tutorsnotebook.utils.NotesStorageHandler
import com.example.tutorsnotebook.views.adapters.NotesAdapter
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView(view)
        super.onViewCreated(view, savedInstanceState)
    }

    private var recyclerView : RecyclerView? = null

    private fun initRecyclerView(rootView : View) {
        recyclerView = rootView.findViewById(R.id.notes_recycler_view)
        recyclerView?.adapter = NotesAdapter(NotesStorageHandler().readNotes(requireContext()))
        recyclerView?.layoutManager = GridLayoutManager(requireContext(), 2)
    }
}