package com.example.tutorsnotebook.views.fragments.tutor

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.tutorsnotebook.R
import com.example.tutorsnotebook.entities.Student
import com.example.tutorsnotebook.utils.GsonHandler
import com.example.tutorsnotebook.utils.ImageHandler

class StudentInfoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_student_info, container, false)

        initUi(rootView, arguments)

        return rootView;
    }

    private fun initUi(rootView: View, args: Bundle?) {
        val paymentStatusImageView = rootView
            .findViewById<ImageView>(R.id.student_info_image_view_payment_status)
        val scoreStatusImageView =
            rootView.findViewById<ImageView>(R.id.student_info_image_view_score_status)
        val nameTextView = rootView
            .findViewById<TextView>(R.id.student_info_edit_text_name)
        val scoreTextView = rootView
            .findViewById<TextView>(R.id.student_info_text_view_score)
        val parentNameTextView = rootView
            .findViewById<TextView>(R.id.student_info_edit_text_parent_name)
        val parentPhoneTextView = rootView
            .findViewById<TextView>(R.id.student_info_edit_text_parent_phone)
        val loginTextView = rootView
            .findViewById<TextView>(R.id.student_info_text_view_login)

        if (args != null) {
            val serializedStudent = args.getString("data")
            if (serializedStudent != null) {
                val student = GsonHandler.deserializeObject<Student>(serializedStudent)

                nameTextView.text = student.name + " " + student.surname
                scoreTextView.text = student.avgScore.toString()
                parentNameTextView.text = student.parentName
                parentPhoneTextView.text = student.parentPhone.toString()
                loginTextView.text = student.key

                setPaymentStatusImage(paymentStatusImageView, student.isPayed)
                setScoreStatusImage(scoreStatusImageView, student.scoreStatus)
            }
        }
    }

    private fun setScoreStatusImage(imageView: ImageView, status: Student.ScoreStatus) {
        val drawableId: Int
        val colorId: Int

        when (status) {
            Student.ScoreStatus.INCREASES -> {
                drawableId = R.drawable.ic_arrow_up_28
                colorId = R.color.green
            }
            Student.ScoreStatus.STAYS -> {
                drawableId = R.drawable.ic_minus_24
                colorId = R.color.dark_gray
            }
            Student.ScoreStatus.DECREASES -> {
                drawableId = R.drawable.ic_arrow_down_28
                colorId = R.color.red
            }
        }

        ImageHandler.setColoredImage(
            requireContext(),
            imageView,
            drawableId,
            colorId
        )
    }

    private fun setPaymentStatusImage(imageView: ImageView, status: Boolean) {
        ImageHandler.setColoredImage(
            requireContext(),
            imageView,
            if (status) R.drawable.ic_check_circle_24 else R.drawable.ic_minus_circle_24,
            if (status) R.color.green else R.color.red
        )
    }
}