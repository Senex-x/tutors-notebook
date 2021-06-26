package com.example.tutorsnotebook.views.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tutorsnotebook.R
import com.example.tutorsnotebook.entities.Student

class StudentsRecyclerAdapter(private val data: List<Student>, private val context: Context) :
    RecyclerView.Adapter<StudentsRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameTextView: TextView? = null
        var scoreTextView: TextView? = null
        var arrowImageView: ImageView? = null

        init {
            nameTextView = itemView.findViewById(R.id.list_item_student_edit_text_name)
            scoreTextView = itemView.findViewById(R.id.list_item_student_edit_text_score)
            arrowImageView = itemView.findViewById(R.id.list_item_student_image_view_arrow)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_student, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCard = data[position]

        initNameEditText(holder, currentCard)
        initScoreTextView(holder, currentCard)
        initArrowImageView(holder, currentCard)
    }

    private fun initNameEditText(holder: ViewHolder, currentStudent: Student) {
        holder.nameTextView?.text = currentStudent.name
    }

    private fun initScoreTextView(holder: ViewHolder, currentStudent: Student) {
        holder.scoreTextView?.text = currentStudent.avgScore.toString()
    }

    private fun initArrowImageView(holder: ViewHolder, currentStudent: Student) {
        val imageView = holder.arrowImageView!!
        when (currentStudent.scoreStatus) {
            Student.ScoreStatus.INCREASES -> {
                setStatusImage(imageView, R.drawable.ic_arrow_up_28, R.color.green,  context)
            }
            Student.ScoreStatus.STAYS -> {
                setStatusImage(imageView, R.drawable.ic_minus_24, R.color.dark_gray,  context)
            }
            Student.ScoreStatus.DECREASES -> {
                setStatusImage(imageView, R.drawable.ic_arrow_down_28, R.color.red,  context)
            }
        }
    }

    private fun setStatusImage(imageView: ImageView, imageId: Int, colorId: Int, context: Context, ) {
        val vectorImage: Drawable = ResourcesCompat.getDrawable(context.resources, imageId, null)!!
        DrawableCompat.setTint(
            DrawableCompat.wrap(vectorImage),
            ContextCompat.getColor(context, colorId)
        )
        imageView.setImageDrawable(vectorImage)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}