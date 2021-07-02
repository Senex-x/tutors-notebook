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
import androidx.navigation.Navigation
import com.example.tutorsnotebook.R
import com.example.tutorsnotebook.database.Database
import com.example.tutorsnotebook.entities.Homework
import com.example.tutorsnotebook.utils.ImageHandler
import com.example.tutorsnotebook.utils.Logger
import com.example.tutorsnotebook.utils.PreferencesHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.FileNotFoundException


class SendHomeworkFragment : Fragment() {
    private val newImagesUriList = ArrayList<Uri>()
    private var imageContainerLayout: LinearLayout? = null
    private var messageEditText: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_send_homework, container, false)

        initUi(rootView)

        return rootView
    }

    private fun initUi(rootView: View) {
        messageEditText = rootView.findViewById(R.id.send_homework_edit_message)
        imageContainerLayout = rootView.findViewById(R.id.check_homework_layout_images)

        val addImageButton = rootView.findViewById<Button>(R.id.send_homework_button_add_image)
        addImageButton.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, 1)
        }

        val submitButton = rootView.findViewById<Button>(R.id.check_homework_button_submit)
        submitButton.setOnClickListener {
            GlobalScope.launch {
                uploadImagesToDatabase()
            }
            Navigation.findNavController(it)
                .navigate(R.id.action_sendHomeworkFragment_to_homeworkFragment)
        }

    }

    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            try {
                val imageUri: Uri? = data.data
                if (imageUri != null && imageContainerLayout != null) {
                    newImagesUriList.add(imageUri)
                    val newImageView = addNewImageView(imageContainerLayout!!)
                    val bitmap = ImageHandler.handleSamplingAndRotationBitmap(
                        requireContext(),
                        imageUri
                    )
                    newImageView.setImageBitmap(bitmap)
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun uploadImagesToDatabase() {
        val context = requireContext()
        val images = ArrayList<String>()
        for (imageUri in newImagesUriList) {
            val bitmap = ImageHandler.getBitmapByUri(imageUri, context)
            val compressedBitmap = ImageHandler.compressBitmap(bitmap, 20)
            val imageString = ImageHandler.imageToString(compressedBitmap)
            images.add(imageString)
        }
        saveHomeworkToDatabase(images)
    }

    private fun saveHomeworkToDatabase(images: ArrayList<String>) {
        val studentKey = PreferencesHandler(requireActivity()).getStudentKey()
        val homework = Homework(studentKey, -1, images, messageEditText?.text.toString())
        Logger.d("Sending homework to database")
        Database.putHomework(homework)
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