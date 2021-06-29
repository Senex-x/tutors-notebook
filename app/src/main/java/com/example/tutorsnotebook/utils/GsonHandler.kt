package com.example.tutorsnotebook.utils

import com.example.tutorsnotebook.entities.CalendarEvent
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class GsonHandler {
    companion object {
        fun <T> serializeList(list: ArrayList<T>): String {
            return Gson().toJson(list)
        }

        fun deserializeListOfCalendarEvents(serializedSource: String): ArrayList<CalendarEvent> {
            val type = object : TypeToken<ArrayList<CalendarEvent>>() {}.type
            return Gson().fromJson(serializedSource, type)
        }
    }
}