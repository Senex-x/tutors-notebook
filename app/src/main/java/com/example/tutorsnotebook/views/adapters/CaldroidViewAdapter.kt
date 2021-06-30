package com.caldroidsample

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.example.tutorsnotebook.R
import com.example.tutorsnotebook.entities.CalendarEvent
import com.example.tutorsnotebook.views.fragments.calendar.CalendarCellClickDialogFragment
import com.example.tutorsnotebook.views.fragments.calendar.CalendarEventClickDialogFragment
import com.roomorama.caldroid.CaldroidFragment
import com.roomorama.caldroid.CaldroidGridAdapter
import hirondelle.date4j.DateTime

class CaldroidViewAdapter(
    private val eventList: ArrayList<CalendarEvent>,
    private val activity: FragmentActivity?, month: Int, year: Int,
    caldroidData: Map<String?, Any?>?,
    extraData: Map<String?, Any?>?
) :
    CaldroidGridAdapter(activity, month, year, caldroidData, extraData) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var cellView = convertView

        // For reuse
        if (convertView == null) {
            cellView = inflater.inflate(R.layout.calendar_cell, null)
        }
        val topPadding = cellView!!.paddingTop
        val leftPadding = cellView.paddingLeft
        val bottomPadding = cellView.paddingBottom
        val rightPadding = cellView.paddingRight
        val tv1 = cellView.findViewById<TextView>(R.id.calendar_cell_text_view_date)
        val tv2 = cellView.findViewById<TextView>(R.id.tv2)
        tv1.setTextColor(Color.BLACK)

        // Get dateTime of this cell
        val dateTime = datetimeList[position]
        val resources = context.resources

        // Set color of the dates in previous / next month
        if (dateTime.month != month) {
            tv1.setTextColor(
                resources
                    .getColor(R.color.caldroid_darker_gray)
            )
        }
        var shouldResetDisabledView = false
        var shouldResetSelectedView = false

        // Customize for disabled dates and date outside min/max dates
        if (minDateTime != null && dateTime.lt(minDateTime)
            || maxDateTime != null && dateTime.gt(maxDateTime)
            || disableDates != null && disableDates.indexOf(dateTime) != -1
        ) {
            tv1.setTextColor(CaldroidFragment.disabledTextColor)
            if (CaldroidFragment.disabledBackgroundDrawable == -1) {
                cellView.setBackgroundResource(R.drawable.disable_cell)
            } else {
                cellView.setBackgroundResource(CaldroidFragment.disabledBackgroundDrawable)
            }
            if (dateTime == getToday()) {
                cellView.setBackgroundResource(R.drawable.rectandle_blue_outline)
            }
        } else {
            shouldResetDisabledView = true
        }

        // Customize for selected dates
        if (selectedDates != null && selectedDates.indexOf(dateTime) != -1) {
            cellView.setBackgroundColor(resources.getColor(R.color.caldroid_sky_blue))
            tv1.setTextColor(Color.BLACK)
        } else {
            shouldResetSelectedView = true
        }
        if (shouldResetDisabledView && shouldResetSelectedView) {
            // Customize for today
            if (dateTime == getToday()) {
                cellView.setBackgroundResource(R.drawable.rectandle_blue_outline)
            } else {
                cellView.setBackgroundResource(R.drawable.cell_bg)
            }
        }

        tv1.text = dateTime.day.toString()
        if (dateTime.month >= month && dateTime.gteq(getToday())) {
            val eventIndex = getEventIndexIfPresent(datetimeList[position])
            if (eventIndex != -1) {
                handlePresentEvent(activity!!, eventList[eventIndex], cellView, tv2, eventList)
            } else {
                handleCreateEvent(activity!!, cellView, tv2, eventList, datetimeList[position])
            }
        } else if (dateTime.lt(getToday())){
            cellView.setBackgroundColor(
                resources
                    .getColor(R.color.caldroid_lighter_gray)
            )
        }

        // Somehow after setBackgroundResource, the padding collapses
        // This is to recover the padding
        cellView.setPadding(
            leftPadding, topPadding, rightPadding,
            bottomPadding
        )

        // Set custom color if required
        //setCustomResources(dateTime, cellView, tv1)
        return cellView
    }

    companion object {
        fun handleCreateEvent(
            activity: FragmentActivity,
            cellView: View,
            cellTextView: TextView,
            eventList: ArrayList<CalendarEvent>,
            dateTime: DateTime
        ) {
            cellView.setOnClickListener {
                val myDialogFragment =
                    CalendarCellClickDialogFragment(
                        activity,
                        cellView,
                        cellTextView,
                        eventList,
                        dateTime
                    )
                val manager = activity.supportFragmentManager
                myDialogFragment.show(manager, "dialogCreate")
            }
        }

        fun handlePresentEvent(
            activity: FragmentActivity,
            calendarEvent: CalendarEvent,
            cellView: View,
            cellTextView: TextView,
            eventList: ArrayList<CalendarEvent>
        ) {
            cellView.setBackgroundResource(R.drawable.rectangle_red_outline)
            cellView.setOnClickListener {
                val myDialogFragment =
                    CalendarEventClickDialogFragment(
                        activity,
                        calendarEvent,
                        cellView,
                        cellTextView,
                        eventList
                    )
                val manager = activity.supportFragmentManager
                myDialogFragment.show(manager, "dialogEvent")
            }
        }
    }

    private fun getEventIndexIfPresent(currentDateTime: DateTime): Int {
        for ((i, event) in eventList.withIndex()) {
            val dateTimeFromList = event.date
            if (currentDateTime.year == dateTimeFromList.year &&
                currentDateTime.month == dateTimeFromList.month &&
                currentDateTime.day == dateTimeFromList.day
            ) {
                return i
            }
        }
        return -1
    }
}