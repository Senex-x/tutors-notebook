package com.example.tutorsnotebook.views.fragments.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tutorsnotebook.R
import com.example.tutorsnotebook.entities.Quiz
import com.example.tutorsnotebook.views.adapters.QuizAdapter


class TestsFragment : Fragment() {
    lateinit var adapter: QuizAdapter
    private var quizList = mutableListOf<Quiz>()
    private var quizRecuclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_tests, container, false)
        initRecyclerView(rootView)

        return rootView;
    }

    private fun initRecyclerView(rootView: View) {
        quizRecuclerView = rootView.findViewById(R.id.quizRecyclerView)
        quizRecuclerView?.layoutManager = GridLayoutManager(requireContext(), 2)
        quizRecuclerView?.adapter = QuizAdapter(requireContext(), quizList)
    }
}
