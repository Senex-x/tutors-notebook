package com.example.tutorsnotebook.views.fragments.calendar

import com.caldroidsample.CaldroidViewAdapter
import com.roomorama.caldroid.CaldroidFragment
import com.roomorama.caldroid.CaldroidGridAdapter

class CaldriodViewFragment : CaldroidFragment() {
    override fun getNewDatesGridAdapter(month: Int, year: Int): CaldroidGridAdapter {
        return CaldroidViewAdapter(
            activity, month, year,
            getCaldroidData(), extraData
        )
    }
}