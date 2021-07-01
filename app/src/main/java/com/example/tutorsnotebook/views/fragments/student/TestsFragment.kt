package com.example.tutorsnotebook.views.fragments.student

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tutorsnotebook.R
import com.example.tutorsnotebook.entities.Quiz
import com.example.tutorsnotebook.views.adapters.QuizAdapter
import com.google.firebase.firestore.FirebaseFirestore


class TestsFragment : Fragment() {
    lateinit var adapter: QuizAdapter
    private var quizList = mutableListOf<Quiz>()
    private var quizRecuclerView: RecyclerView? = null
    lateinit var firestore: FirebaseFirestore


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_tests, container, false)
        initFireStore()
        initRecyclerView(rootView)

        return rootView;
    }

    private fun initFireStore() {
        firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("quizzes")
        collectionReference.addSnapshotListener{ value, error ->
            if (value == null || error != null) {
                Toast.makeText(getActivity(), "Error fetching data", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            Log.d("DATA", value.toObjects(Quiz::class.java).toString())
            quizList.clear()
            quizList.addAll(value.toObjects(Quiz::class.java))
            adapter.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView(rootView: View) {
        quizRecuclerView = rootView.findViewById(R.id.quizRecyclerView)
        quizRecuclerView?.layoutManager = GridLayoutManager(requireContext(), 2)
        quizRecuclerView?.adapter = QuizAdapter(requireContext(), quizList)
    }
}
