package com.example.tutorsnotebook.views.fragments.calendar

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.caldroidsample.CaldroidViewAdapter
import com.example.tutorsnotebook.R
import com.example.tutorsnotebook.entities.CalendarEvent
import hirondelle.date4j.DateTime


class CalendarCellClickDialogFragment(
    private val activityFragment: FragmentActivity,
    private val cellView: View,
    private val cellTextView: TextView,
    private val eventList: ArrayList<CalendarEvent>,
    private val dateTime: DateTime
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val editText = EditText(context)
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Создать событие")
                .setView(editText)
                .setNegativeButton("Создать") { dialog, id ->
                    val newEvent = CalendarEvent(
                        dateTime,
                        editText.text.toString()
                    )
                    CaldroidViewAdapter.handlePresentEvent(activityFragment, newEvent, cellView, cellTextView, eventList)
                    eventList.add(newEvent)
                    dialog.cancel()
                }
                .setPositiveButton("Отмена") { dialog, id ->
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}