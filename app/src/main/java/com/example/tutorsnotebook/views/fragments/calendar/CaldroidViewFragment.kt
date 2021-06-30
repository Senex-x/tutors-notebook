package com.example.tutorsnotebook.views.fragments.calendar

import com.caldroidsample.CaldroidViewAdapter
import com.example.tutorsnotebook.entities.CalendarEvent
import com.roomorama.caldroid.CaldroidFragment
import com.roomorama.caldroid.CaldroidGridAdapter

class CaldroidViewFragment(
    private val data: ArrayList<CalendarEvent>
) : CaldroidFragment() {
    override fun getNewDatesGridAdapter(month: Int, year: Int): CaldroidGridAdapter {
        return CaldroidViewAdapter(
            data, activity, month, year,
            getCaldroidData(), extraData
        )
    }
}