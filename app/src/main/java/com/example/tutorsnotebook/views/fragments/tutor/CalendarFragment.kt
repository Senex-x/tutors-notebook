package com.example.tutorsnotebook.views.fragments.tutor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tutorsnotebook.R
import com.example.tutorsnotebook.entities.CalendarEvent
import com.example.tutorsnotebook.utils.FileHandler
import com.example.tutorsnotebook.utils.GsonHandler
import com.example.tutorsnotebook.utils.Logger
import com.example.tutorsnotebook.views.fragments.calendar.CaldroidViewFragment
import com.roomorama.caldroid.CaldroidFragment
import hirondelle.date4j.DateTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random


class CalendarFragment : Fragment() {
    private var calendarEvents: ArrayList<CalendarEvent>? = null
    private var todayDateTime: DateTime? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_calendar, container, false)

        // Put initializers here

        return rootView;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        calendarEvents = getCalendarEvents()

        todayDateTime = DateTime.today(TimeZone.getDefault())
        // Won't be needed when events will be from storage
        removeOldEvents(todayDateTime!!)

        val caldroidFragment = CaldroidViewFragment(calendarEvents!!)

        val args = Bundle()
        val cal = Calendar.getInstance()
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1)
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR))
        args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true)
        args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true)
        args.putInt(CaldroidFragment.START_DAY_OF_WEEK, CaldroidFragment.MONDAY)
        caldroidFragment.arguments = args

        // Attach to the activity
        val t = requireActivity().supportFragmentManager.beginTransaction()
        t.replace(R.id.calendar_layout_placeholder, caldroidFragment)
        t.commit()
    }

    override fun onDetach() {
        super.onDetach()

        removeOldEvents(todayDateTime!!)
        saveCalendarEvents()
    }

    private fun getCalendarEvents(): ArrayList<CalendarEvent> {
        // Should be replaced with DB access
        val serializedData =
            FileHandler(requireContext())
                .getTextFromFile(FileHandler.CALENDAR_STATE_FILE_NAME)
        return if (serializedData.isNotEmpty()) {
            GsonHandler.deserializeListOfCalendarEvents(serializedData)
        } else {
            ArrayList()
        }
    }

    private fun saveCalendarEvents() {
        // do something to save new list
        FileHandler(requireContext()).saveTextToFile(
            FileHandler.CALENDAR_STATE_FILE_NAME,
            GsonHandler.serializeList(calendarEvents!!)
        )
    }

    private fun removeOldEvents(
        todayDateTime: DateTime
    ) {
        val newCalendarEvents = ArrayList<CalendarEvent>()
        for (event in calendarEvents!!) {
            if (event.date.lt(todayDateTime)) {
                newCalendarEvents.add(event)
            }
        }
    }

    companion object {
        private fun generateCalendarEvents(): ArrayList<CalendarEvent> { // private
            val list = ArrayList<CalendarEvent>()
            for (i in 0..10) {
                list.add(
                    CalendarEvent(
                        DateTime(
                            2021,
                            Random.nextInt(6, 8),
                            Random.nextInt(1, 30),
                            0,
                            0,
                            0,
                            0
                        ),
                        "Sample event $i"
                    )
                )
            }
            return list
        }
    }
}
