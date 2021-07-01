package com.example.tutorsnotebook.views.fragments.tutor

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.tutorsnotebook.R
import com.example.tutorsnotebook.database.Database
import com.example.tutorsnotebook.utils.ImageHandler
import com.example.tutorsnotebook.utils.PreferencesHandler
import com.example.tutorsnotebook.utils.Toaster
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CheckHomeworkFragment : Fragment() {
    private val studentKey = PreferencesHandler(requireActivity()).getStudentKey()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_check_homework, container, false)

        initUi(rootView)

        return rootView
    }

    private fun initUi(rootView: View) {
        val imagesPreviewLayout =
            rootView.findViewById<LinearLayout>(R.id.check_homework_layout_images)
        val scoreEditText = rootView.findViewById<EditText>(R.id.check_homework_edit_score)

        val submitButton = rootView.findViewById<Button>(R.id.check_homework_button_submit)
        submitButton.setOnClickListener {
            val score = scoreEditText.text.toString()
            if (score.isNotEmpty()) {
                saveScoreToDatabase(score.toInt())

                Navigation.findNavController(it)
                    .navigate(R.id.action_checkHomeworkFragment_to_studentsFragment)
            } else {
                Toaster.toast("Оценка не выставлена", requireContext())
            }
        }

        // Gonna take time
        GlobalScope.launch {
            handleHomeworkDisplay(imagesPreviewLayout)
        }
    }

    private fun handleHomeworkDisplay(parent: LinearLayout) {
        val homework = Database.getHomeworkFromDatabase(studentKey)
        val images = homework.images
        for (image in images) {
            addNewImageView(parent).setImageBitmap(
                ImageHandler.stringToBitmap(image)
            )
        }
    }

    private fun saveScoreToDatabase(score: Int) {
        Database.putHomeworkScore(studentKey, score)
    }

    private fun addNewImageView(parent: LinearLayout): ImageView {
        val newImageView = ImageView(requireContext())
        newImageView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        parent.addView(newImageView)
        return newImageView
    }
}