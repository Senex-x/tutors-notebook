package com.example.tutorsnotebook.views.fragments.tutor

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tutorsnotebook.R
import com.example.tutorsnotebook.entities.CalendarEvent
import com.example.tutorsnotebook.views.fragments.calendar.CaldriodViewFragment
import com.roomorama.caldroid.CaldroidFragment
import com.roomorama.caldroid.CaldroidListener
import hirondelle.date4j.DateTime
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random


class CalendarFragment : Fragment() {
    private var undo = false
    private var caldroidFragment: CaldriodViewFragment? = null
    private var dialogCaldroidFragment: CaldriodViewFragment? = null

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
        val formatter = SimpleDateFormat("dd MMM yyyy");

        caldroidFragment = CaldriodViewFragment(getCalendarEvents())


        // If activity is created from fresh

            val args = Bundle()
            val cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1)
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR))
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true)
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true)
            args.putInt(CaldroidFragment.START_DAY_OF_WEEK, CaldroidFragment.MONDAY); // Tuesday

            caldroidFragment?.arguments = args;


        // Attach to the activity
        val t = requireActivity().supportFragmentManager.beginTransaction()
        t.replace(R.id.calendar_layout_placeholder, caldroidFragment!! as Fragment)
        t.commit()
        }


    private fun getCalendarEvents(): ArrayList<CalendarEvent> {
        // Should be replaced with DB access
        val list = generateCalendarEvents()
        return list
    }

    private fun generateCalendarEvents(): ArrayList<CalendarEvent> {
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

    // TODO: implement
    private fun deleteOldEvents() {

    }

    /**
     * Save current states of the Caldroid here
     */

}
