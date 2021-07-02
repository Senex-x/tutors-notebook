package com.example.tutorsnotebook.views.fragments.tutor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.tutorsnotebook.R
import com.example.tutorsnotebook.database.Database
import com.example.tutorsnotebook.database.OnDataGetListener
import com.example.tutorsnotebook.entities.Homework
import com.example.tutorsnotebook.utils.ImageHandler
import com.example.tutorsnotebook.utils.Logger
import com.example.tutorsnotebook.utils.Toaster
import com.google.firebase.database.DataSnapshot

class CheckHomeworkFragment : Fragment() {
    private var studentKey: String = ""
    private var messageTitleTextView: TextView? = null
    private var messageTextView: TextView? = null
    private var scoreEditText: EditText? = null
    private var submitButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_check_homework, container, false)

        studentKey = requireArguments().getString("student-key")!!

        initUi(rootView)

        return rootView
    }

    private fun initUi(rootView: View) {
        messageTextView =
            rootView.findViewById(R.id.check_homework_text_message)
        messageTitleTextView =
            rootView.findViewById(R.id.check_homework_text_message_title)

        val imagesPreviewLayout =
            rootView.findViewById<LinearLayout>(R.id.check_homework_layout_images)
        scoreEditText = rootView.findViewById(R.id.check_homework_edit_score)

        submitButton = rootView.findViewById(R.id.check_homework_button_submit)
        submitButton?.setOnClickListener {
            val score = scoreEditText?.text.toString()
            if (score.isNotEmpty()) {
                val scoreInt = score.toInt()
                if (scoreInt in 0..100) {
                    saveScoreToDatabase(scoreInt)
                    handleStudentScoreUpdate(scoreInt)

                    Navigation.findNavController(it)
                        .navigate(R.id.action_checkHomeworkFragment_to_studentsFragment)
                } else Toaster.toast("Оценка некорректна", requireContext())
            } else Toaster.toast("Оценка не выставлена", requireContext())
        }

        // Gonna take time
        handleHomeworkDisplay(imagesPreviewLayout)
    }

    private fun handleHomeworkDisplay(parent: LinearLayout) {
        Database.getHomework(studentKey, object : OnDataGetListener {
            override fun onSuccess(data: DataSnapshot?) {
                val homework = data!!.getValue(Homework::class.java)!!
                val images = homework.images
                for (image in images) {
                    addNewImageView(parent).setImageBitmap(
                        ImageHandler.stringToBitmap(image)
                    )
                }

                if (homework.score != -1) {
                    Toaster.toast("Работа уже проверена", requireContext())
                    scoreEditText?.setText(homework.score.toString())
                    scoreEditText?.setTextColor(requireContext().resources.getColor(R.color.dark_gray))
                    scoreEditText?.isEnabled = false

                    submitButton?.setBackgroundColor(requireContext().resources.getColor(R.color.dark_gray))
                    submitButton?.setOnClickListener {
                        Toaster.toast("Ожидаем отправки учеником новой работы", requireContext())
                    }
                    submitButton?.text = "ожидание"
                }
                if(homework.message.isNotEmpty()) {
                    messageTitleTextView?.visibility = View.VISIBLE
                    messageTextView?.visibility = View.VISIBLE
                    messageTextView?.text = homework.message
                }
            }
        })
    }

    private fun handleStudentScoreUpdate(additionalScore: Int) {
        Database.getStudent(studentKey) {
            it.addScore(additionalScore)
            Database.putStudent(it)
            Logger.d("Student score updated")
        }
    }

    private fun saveScoreToDatabase(score: Int) {
        Database.putHomeworkScore(studentKey, score)
    }

    private fun addNewImageView(parent: LinearLayout): ImageView {
        val newImageView = ImageView(requireContext())
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.topMargin = 16
        newImageView.layoutParams = params

        parent.addView(newImageView)
        return newImageView
    }
}