package com.example.tutorsnotebook.views.fragments.tutor

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
import com.example.tutorsnotebook.utils.ImageHandler
import com.example.tutorsnotebook.utils.StudentsRecyclerClickListener
import com.example.tutorsnotebook.views.adapters.StudentsRecyclerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.roomorama.caldroid.CaldroidFragment
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class StudentsFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var studentsList: ArrayList<Student>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_students, container, false)

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
            ImageHandler.getColoredDrawable(
                requireContext(),
                R.drawable.ic_add_24,
                R.color.primary
            )
        )
    }

    private fun getCardsData(): ArrayList<Student> {
        studentsList = generateRandomCardsData()
        return studentsList!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView(view)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initRecyclerView(rootView: View) {
        recyclerView = rootView.findViewById(R.id.students_recycler_view)
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        Database.getAllStudents {
            recyclerView?.adapter = StudentsRecyclerAdapter(it, requireContext())
            studentsList = ArrayList(it)
        }
        recyclerView?.addOnItemTouchListener(
            StudentsRecyclerClickListener(
                context,
                recyclerView!!,
                object : StudentsRecyclerClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        openStudent(studentsList!![position], view!!)
                    }

                    override fun onLongItemClick(view: View?, position: Int) {
                        // TODO: add option to delete student for example
                        Toast.makeText(context, "*Long click indicator*", Toast.LENGTH_SHORT).show()
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

    private fun generateRandomCardsData(): ArrayList<Student> {
        val students: ArrayList<Student> = ArrayList()
        for (i in 0..10) {
            students.add(
                Student(
                    "SampleKey $i",
                    "Name $i",
                    "Surname $i",
                    i.toLong(),
                    "Parent name $i",
                    i.toLong(),
                    Random.nextBoolean(),
                    Random.nextInt(100),
                    10,
                    Student.ScoreStatus.values()[Random.nextInt(3)]
                )
            )
        }
        return students
    }
}

