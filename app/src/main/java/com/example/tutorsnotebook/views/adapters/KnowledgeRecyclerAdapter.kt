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

class KnowledgeRecyclerAdapter(private val data: List<String>, private val context: Context) :
    RecyclerView.Adapter<KnowledgeRecyclerAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var fileNameTextView: TextView? = null

        init {
            fileNameTextView = itemView.findViewById(R.id.list_item_knowledge_text_file_name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_knowledge, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentFileName = data[position]
        initFileNameTextView(holder, currentFileName)
    }

    private fun initFileNameTextView(holder: ViewHolder, currentFileName: String) {
        holder.fileNameTextView?.text = currentFileName
    }

    override fun getItemCount(): Int {
        return data.size
    }
}