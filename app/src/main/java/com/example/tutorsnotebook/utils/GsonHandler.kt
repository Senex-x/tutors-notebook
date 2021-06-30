package com.example.tutorsnotebook.utils

import com.example.tutorsnotebook.entities.CalendarEvent
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class GsonHandler {
    companion object {
        val gson = Gson()

        fun <T> serializeObject(generic: T): String {
            return gson.toJson(generic)
        }

        inline fun <reified T> deserializeObject(serializedSource: String): T {
            return gson.fromJson(serializedSource, T::class.java)
        }

        fun <T> serializeList(list: ArrayList<T>): String {
            return gson.toJson(list)
        }

        fun deserializeListOfCalendarEvents(serializedSource: String): ArrayList<CalendarEvent> {
            val type = object : TypeToken<ArrayList<CalendarEvent>>() {}.type
            return gson.fromJson(serializedSource, type)
        }
    }
}