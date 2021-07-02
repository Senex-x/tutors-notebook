package com.example.tutorsnotebook.views.fragments.student

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tutorsnotebook.R
import com.example.tutorsnotebook.database.Database
import com.example.tutorsnotebook.utils.CustomRecyclerClickListener
import com.example.tutorsnotebook.utils.IconHandler
import com.example.tutorsnotebook.views.adapters.KnowledgeRecyclerAdapter
import com.example.tutorsnotebook.views.adapters.StudentsRecyclerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.StringBuilder
import kotlin.random.Random

class KnowledgeFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var contextState: Context? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_knowledge, container, false)
        contextState = requireContext()

        initRecyclerView(rootView)
        initFab(rootView)

        return rootView
    }

    private fun initFab(rootView: View) {
        val fab: FloatingActionButton = rootView.findViewById(R.id.students_fab_add)
        fab.setOnClickListener { view ->

        }
        fab.setImageDrawable(
            IconHandler.getColoredDrawable(
                requireContext(),
                R.drawable.ic_add_24,
                R.color.primary
            )
        )
    }

    private fun initRecyclerView(rootView: View) {
        recyclerView = rootView.findViewById(R.id.knowledge_recycler_view)
        recyclerView?.layoutManager = LinearLayoutManager(contextState)
        recyclerView?.adapter = KnowledgeRecyclerAdapter(getFileNameList(), contextState!!)
        recyclerView?.addOnItemTouchListener(
            CustomRecyclerClickListener(
                contextState,
                recyclerView!!,
                object : CustomRecyclerClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        // Navigate to another fragment
                    }

                    override fun onLongItemClick(view: View?, position: Int) {}
                })
        )
    }

    private fun getFileNameList(): List<String> {
        return generateRandomFileNameList()
    }

    private fun generateRandomFileNameList(): List<String> {
        val list = ArrayList<String>()
        for (i in 0..10) {
            val string = StringBuilder()
            for (j in 0..20) {
                string.append(Random.nextInt(30, 200).toChar())
            }
            list.add(string.toString())
        }
        return list
    }
}