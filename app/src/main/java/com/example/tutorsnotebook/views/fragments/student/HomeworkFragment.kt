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
import com.example.tutorsnotebook.utils.OnItemGetListener
import com.example.tutorsnotebook.utils.PreferencesHandler

class HomeworkFragment : Fragment() {
    var studentKey: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_homework, container, false)

        studentKey = PreferencesHandler(requireActivity()).getStudentKey()
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
        val avgScoreTextView = rootView.findViewById<TextView>(R.id.homework_text_avg_score)

        val studentKey = PreferencesHandler(requireActivity()).getStudentKey()
        Database.getHomeworkScore(studentKey, object : OnItemGetListener<Int> {
            override fun onSuccess(item: Int) {
                if (item == -1) { // Not checked yet
                    statusTextView.text = "Ожидает проверки"
                    lastScoreTextView.text = "Ожидание проверки"
                    sendButton.text = "Отправить заново"
                } else {
                    statusTextView.text = "Ожидает отправки"
                    lastScoreTextView.text = item.toString()
                }
            }
        })

        Database.getStudentAverageScore(studentKey, object : OnItemGetListener<Int> {
            override fun onSuccess(item: Int) {
                avgScoreTextView.text = item.toString()
            }
        })
    }
}