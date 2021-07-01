package com.example.tutorsnotebook.views.fragments.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.tutorsnotebook.R
import com.example.tutorsnotebook.database.Database
import com.example.tutorsnotebook.database.OnDataGetListener
import com.example.tutorsnotebook.entities.Homework
import com.example.tutorsnotebook.utils.PreferencesHandler
import com.google.firebase.database.DataSnapshot

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

        val statusTextView = rootView.findViewById<TextView>(R.id.homework_text_status)
        val lastScoreTextView = rootView.findViewById<TextView>(R.id.homework_text_last_score)

        val studentKey = PreferencesHandler(requireActivity()).getStudentKey()
        Database.getHomeworkScore(studentKey, object : OnDataGetListener {
            override fun onSuccess(data: DataSnapshot?) {
                if(data != null) {
                    val score = data.getValue(Int::class.java)!!
                    if(score == -1) { // Not checked yet
                        statusTextView.text = "Ожидает проверки"
                        lastScoreTextView.text = "Ожидание проверки"
                    } else {
                        statusTextView.text = "Ожидает отправки"
                        lastScoreTextView.text = score.toString()
                    }
                }
            }
        })

    }
}