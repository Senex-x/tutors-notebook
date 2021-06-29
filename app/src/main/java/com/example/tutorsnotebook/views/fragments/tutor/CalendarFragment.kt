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

    private fun setCustomResourceForDates() {
        var cal: Calendar = Calendar.getInstance()

        // Min date is last 7 days
        cal.add(Calendar.DATE, -7)
        val blueDate: Date = cal.getTime()

        // Max date is next 7 days
        cal = Calendar.getInstance()
        cal.add(Calendar.DATE, 7)
        val greenDate: Date = cal.getTime()
        if (caldroidFragment != null) {
            val blue = ColorDrawable(Color.BLUE)
            val green = ColorDrawable(Color.GREEN)
            caldroidFragment?.setBackgroundDrawableForDate(blue, blueDate)
            caldroidFragment?.setBackgroundDrawableForDate(green, greenDate)
            caldroidFragment?.setTextColorForDate(R.color.white, blueDate)
            caldroidFragment?.setTextColorForDate(R.color.white, greenDate)
        }
    }


    private fun addCustomData() {
        val yourCustomData1 = "Custom 1"
        val yourCustomData2 = "Custom 2"

        // To set the extraData:
        val extraData: MutableMap<String, Any>? = caldroidFragment!!.extraData
        extraData?.set("YOUR_CUSTOM_DATA_KEY1", yourCustomData1)
        extraData?.set("YOUR_CUSTOM_DATA_KEY2", yourCustomData2)

// Refresh view
        caldroidFragment!!.refreshView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val formatter = SimpleDateFormat("dd MMM yyyy");

        caldroidFragment = CaldriodViewFragment(getCalendarEvents())

        // If Activity is created after rotation
        if (savedInstanceState != null) {
            caldroidFragment?.restoreStatesFromKey(
                savedInstanceState,
                "CALDROID_SAVED_STATE"
            );
        }

        // If activity is created from fresh
        else {
            val args = Bundle()
            val cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1)
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR))
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true)
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true)
            args.putInt(CaldroidFragment.START_DAY_OF_WEEK, CaldroidFragment.MONDAY); // Tuesday

            caldroidFragment?.arguments = args;
        }

        // Attach to the activity
        val t = requireActivity().supportFragmentManager.beginTransaction()
        t.replace(R.id.calendar_layout_placeholder, caldroidFragment!! as Fragment)
        t.commit()

        val activity = requireActivity()

        // Setup listener
        val listener = object : CaldroidListener() {
            override fun onSelectDate(date: Date, view: View) {
                Toast.makeText(
                    activity.applicationContext, formatter.format(date),
                    Toast.LENGTH_SHORT
                ).show()
            }


            override fun onChangeMonth(month: Int, year: Int) {
                val text = "month: $month year: $year"
                Toast.makeText(
                    activity.applicationContext, text,
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onLongClickDate(date: Date, view: View) {
                Toast.makeText(
                    activity.applicationContext,
                    "Long click " + formatter.format(date),
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onCaldroidViewCreated() {
                if (caldroidFragment?.leftArrowButton != null) {
                    Toast.makeText(
                        activity.applicationContext,
                        "Caldroid view is created", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // Setup Caldroid
        caldroidFragment?.caldroidListener = listener

        val textView = activity.findViewById<TextView>(R.id.textview)

        val customizeButton = activity.findViewById<Button>(R.id.customize_button)

        // Customize the calendar
        customizeButton.setOnClickListener {
            if (undo) {
                customizeButton.text = "Customize"
                textView.text = ""

                // Reset calendar
                caldroidFragment?.clearDisableDates()
                caldroidFragment?.clearSelectedDates()
                caldroidFragment?.setMinDate(null)
                caldroidFragment?.setMaxDate(null)
                caldroidFragment?.isShowNavigationArrows = true
                caldroidFragment?.isEnableSwipe = true
                caldroidFragment?.refreshView()
                undo = false
            } else {
                undo = true;
                customizeButton.setText("Undo")
                val cal = Calendar.getInstance()

                // Min date is last 7 days
                cal.add(Calendar.DATE, 0)
                val minDate = cal.time

                // Max date is next amount of days
                //cal = Calendar.getInstance()
                cal.add(Calendar.DATE, 60)
                val maxDate = cal.time

                // Set selected dates
                // From Date
                //cal = Calendar.getInstance()
                cal.add(Calendar.DATE, 2)
                val fromDate = cal.time

                // To Date
                //cal = Calendar.getInstance()
                cal.add(Calendar.DATE, 3)
                val toDate = cal.time

                // Set disabled dates
                /*
                val disabledDates = ArrayList<Date>()
                for (i in 5..8) {
                    //cal = Calendar.getInstance()
                    cal.add(Calendar.DATE, i)
                    disabledDates.add(cal.time)
                }*/

                // Customize
                caldroidFragment?.setMinDate(minDate)
                //caldroidFragment?.setMaxDate(maxDate)
                //caldroidFragment?.setDisableDates(disabledDates)
                //caldroidFragment?.setSelectedDates(fromDate, toDate)
                //caldroidFragment?.setShowNavigationArrows(false)
                //caldroidFragment?.setEnableSwipe(false)
                caldroidFragment?.refreshView()

                // Move to date
                // cal = Calendar.getInstance();
                // cal.add(Calendar.MONTH, 12);
                // caldroidFragment.moveToDate(cal.getTime());

                var text = "Today: " + formatter.format(Date()) + "\n"
                text += "Min Date: " + formatter.format(minDate) + "\n"
                text += "Max Date: " + formatter.format(maxDate) + "\n"
                text += "Select From Date: " + formatter.format(fromDate) + "\n"
                text += "Select To Date: " + formatter.format(toDate) + "\n"
/*
                    for (date in disabledDates) {
                        text += "Disabled Date: " + formatter.format(date) + "\n"
                    }
*/
                textView.text = text;
            }
        }
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

    override fun onSaveInstanceState(outState: Bundle) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);

        if (caldroidFragment != null) {
            caldroidFragment?.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
        }

        if (dialogCaldroidFragment != null) {
            dialogCaldroidFragment?.saveStatesToKey(
                outState,
                "DIALOG_CALDROID_SAVED_STATE"
            );
        }
    }
}
