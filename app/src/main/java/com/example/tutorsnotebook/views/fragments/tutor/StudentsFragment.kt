package com.example.tutorsnotebook.views.fragments.tutor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tutorsnotebook.R
import com.example.tutorsnotebook.entities.Student
import com.example.tutorsnotebook.views.adapters.StudentsRecyclerAdapter
import kotlin.random.Random

class StudentsFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var data: ArrayList<Student>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_students, container, false)
        return rootView;
    }

    private fun getCardsData(): ArrayList<Student> {
        return generateRandomCardsData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.students_recycler_view)
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        recyclerView?.adapter = StudentsRecyclerAdapter(getCardsData())
        super.onViewCreated(view, savedInstanceState)
    }

    private fun generateRandomCardsData(): ArrayList<Student> {
        val students: ArrayList<Student> = ArrayList()
        for (i in 0..100) {
            students.add(
                Student(
                    "SampleKey $i",
                    "SampleName $i",
                    "SampleSurname $i",
                    i,
                    i,
                    Random.nextInt(100),
                    10,
                    Random.nextBoolean()
                )
            )
        }
        return students
    }
}

