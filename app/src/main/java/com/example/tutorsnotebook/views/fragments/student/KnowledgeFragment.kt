package com.example.tutorsnotebook.views.fragments.student

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.tutorsnotebook.R
import com.example.tutorsnotebook.utils.ImageHandler
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.core.Context

class KnowledgeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_knowledge, container, false)

        // Put initializers here

        initFab(rootView)

        return rootView;
    }

    private fun initFab(rootView: View) {
        val fab: FloatingActionButton = rootView.findViewById(R.id.students_fab_add)
        fab.setOnClickListener { view ->

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