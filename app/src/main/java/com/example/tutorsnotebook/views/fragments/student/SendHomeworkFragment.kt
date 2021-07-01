package com.example.tutorsnotebook.views.fragments.student

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.tutorsnotebook.R
import com.example.tutorsnotebook.utils.ImageHandler
import java.io.FileNotFoundException


class SendHomeworkFragment : Fragment() {
    var imageContainerLayout: LinearLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_send_homework, container, false)

        // Put initializers here
        initUi(rootView)

        return rootView
    }

    private fun initUi(rootView: View) {
        imageContainerLayout = rootView.findViewById(R.id.send_homework_layout_images)

        val addImageButton = rootView.findViewById<Button>(R.id.send_homework_button_add_image)

        addImageButton.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, 1)
        }
    }

    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            try {
                val imageUri: Uri? = data.data

                val newImageView = addNewImageView(imageContainerLayout!!)

                val bitmap = ImageHandler.handleSamplingAndRotationBitmap(
                    requireContext(),
                    imageUri
                )

                newImageView.setImageBitmap(bitmap)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(requireContext(), "You haven't picked Image", Toast.LENGTH_LONG).show()
        }
    }

    private fun addNewImageView(parent: LinearLayout): ImageView {
        val newImageView = ImageView(requireContext())

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        newImageView.layoutParams = params


        parent.addView(newImageView)
        return newImageView
    }
}