package com.example.tutorsnotebook.views.fragments.calendar

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.caldroidsample.CaldroidViewAdapter
import com.example.tutorsnotebook.R
import com.example.tutorsnotebook.entities.CalendarEvent

class CalendarEventClickDialogFragment(
    private val activityFragment: FragmentActivity,
    private val calendarEvent: CalendarEvent,
    private val cellView: View,
    private val cellTextView: TextView,
    private val eventList: ArrayList<CalendarEvent>
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activityFragment.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Событие")
                .setMessage(calendarEvent.text)
                .setNegativeButton("Ок") { dialog, id ->
                    dialog.cancel()
                }
                .setPositiveButton("Удалить") { dialog, id ->
                    cellView.setOnClickListener {
                        // create new note dialog call
                        CaldroidViewAdapter.handleCreateEvent(activityFragment, cellView, cellTextView, eventList, calendarEvent.date)
                    }
                    eventList.remove(calendarEvent)
                    cellView.setBackgroundResource(R.drawable.rectangle_white)
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
