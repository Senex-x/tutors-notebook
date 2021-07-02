package com.example.tutorsnotebook.views.fragments.tutor

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tutorsnotebook.R
import com.example.tutorsnotebook.database.Database
import com.example.tutorsnotebook.entities.Student
import com.example.tutorsnotebook.utils.GsonHandler
import com.example.tutorsnotebook.utils.IconHandler
import com.example.tutorsnotebook.utils.CustomRecyclerClickListener
import com.example.tutorsnotebook.views.adapters.StudentsRecyclerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class StudentsFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var studentsList: ArrayList<Student>? = null
    private var contextState: Context? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_students, container, false)

        contextState = requireContext()
        initFabAdd(rootView)

        return rootView
    }

    private fun initFabAdd(rootView: View) {
        val fab: FloatingActionButton = rootView.findViewById(R.id.students_fab_add)
        fab.setOnClickListener { view ->
            Navigation.findNavController(view)
                .navigate(R.id.action_studentsFragment_to_addStudentFragment)
        }
        fab.setImageDrawable(
            IconHandler.getColoredDrawable(
                requireContext(),
                R.drawable.ic_add_24,
                R.color.primary
            )
        )
    }

//    private fun getCardsData(): ArrayList<Student> {
//        studentsList = generateRandomCardsData()
//        return studentsList!!
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView(view)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initRecyclerView(rootView: View) {
        recyclerView = rootView.findViewById(R.id.students_recycler_view)
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        //studentsList = generateRandomCardsData()
        Database.getAllStudents {
            recyclerView?.adapter = StudentsRecyclerAdapter(it, contextState!!)
            studentsList = ArrayList(it)
            //studentsList = generateRandomCardsData()
        }
        recyclerView?.addOnItemTouchListener(
            CustomRecyclerClickListener(
                contextState,
                recyclerView!!,
                object : CustomRecyclerClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        openStudent(studentsList!![position], view!!)
                    }

                    override fun onLongItemClick(view: View?, position: Int) {

                    }
                })
        )
    }

    private fun openStudent(student: Student, view: View) {
        val args = Bundle()
        args.putString(
            "data",
            GsonHandler.serializeObject(student)
        )

        Navigation.findNavController(view)
            .navigate(R.id.action_studentsFragment_to_studentInfoFragment, args)
    }
}

