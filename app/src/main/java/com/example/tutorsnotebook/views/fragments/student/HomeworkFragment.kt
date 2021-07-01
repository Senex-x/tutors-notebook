package com.example.tutorsnotebook.views.fragments.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.tutorsnotebook.R

class HomeworkFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_homework, container, false)

        // Put initializers here
        initUi(rootView)

        return rootView
    }

    private fun initUi(rootView: View) {
        val sendButton = rootView.findViewById<Button>(R.id.homework_button_send)

        sendButton.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_homeworkFragment_to_sendHomeworkFragment)
        }
    }
}