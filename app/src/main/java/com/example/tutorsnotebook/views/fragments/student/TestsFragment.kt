package com.example.tutorsnotebook.views.fragments.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
]import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tutorsnotebook.R
import com.example.tutorsnotebook.entities.Quiz
import com.example.tutorsnotebook.views.adapters.QuizAdapter


class TestsFragment : Fragment() {
    lateinit var adapter: QuizAdapter
    private var quizList = mutableListOf<Quiz>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_tests, container, false)
        setUpViews()

        return rootView;
    }

    fun setUpViews() {
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        adapter = QuizAdapter(requireActivity(), quizList)
        quizRecyclerView.LayoutManager = GridLayoutManager(requireActivity(), 2)
        quizRecyclerView.adapter = adapter
    }

}
