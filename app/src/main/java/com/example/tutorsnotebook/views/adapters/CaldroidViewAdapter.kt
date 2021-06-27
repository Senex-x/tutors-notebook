package com.caldroidsample

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.tutorsnotebook.R
import com.roomorama.caldroid.CaldroidFragment
import com.roomorama.caldroid.CaldroidGridAdapter

class CaldroidViewAdapter(
    context: Context?, month: Int, year: Int,
    caldroidData: Map<String?, Any?>?,
    extraData: Map<String?, Any?>?
) :
    CaldroidGridAdapter(context, month, year, caldroidData, extraData) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get your data here
        val yourCustomData1 = extraData["YOUR_CUSTOM_DATA_KEY1"] as String? // works
        val yourCustomData2 = extraData["YOUR_CUSTOM_DATA_KEY2"] as String?
/*
        Toast.makeText(
            context.applicationContext,
            "Custom data received: \n$yourCustomData1 \n$yourCustomData2",
            Toast.LENGTH_LONG
        ).show()*/
        // Continue to build your customized view

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
        // TODO: update to receive data from data structure
        val tv1 = cellView.findViewById<View>(R.id.tv1) as TextView
        val tv2 = cellView.findViewById<View>(R.id.tv2) as TextView
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
        var shouldResetDiabledView = false
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
                cellView.setBackgroundResource(R.drawable.red_border_gray_bg)
            }
        } else {
            shouldResetDiabledView = true
        }

        // Customize for selected dates
        if (selectedDates != null && selectedDates.indexOf(dateTime) != -1) {
            cellView.setBackgroundColor(resources.getColor(R.color.caldroid_sky_blue))
            tv1.setTextColor(Color.BLACK)
        } else {
            shouldResetSelectedView = true
        }
        if (shouldResetDiabledView && shouldResetSelectedView) {
            // Customize for today
            if (dateTime == getToday()) {
                cellView.setBackgroundResource(R.drawable.red_border)
            } else {
                cellView.setBackgroundResource(R.drawable.cell_bg)
            }
        }
        tv1.text = "" + dateTime.day
        tv2.text = "Hi"

        // Somehow after setBackgroundResource, the padding collapse.
        // This is to recover the padding
        cellView.setPadding(
            leftPadding, topPadding, rightPadding,
            bottomPadding
        )

        // Set custom color if required
        setCustomResources(dateTime, cellView, tv1)
        return cellView
    }
}