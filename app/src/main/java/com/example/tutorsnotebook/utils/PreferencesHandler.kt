package com.example.tutorsnotebook.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.example.tutorsnotebook.views.activities.AuthActivity

class PreferencesHandler(
    activity: Activity,
) {
    private val preferences: SharedPreferences = activity.getSharedPreferences(AuthActivity.PREFERENCES_FILE, Activity.MODE_PRIVATE)

    fun getStudentKey(): String {
        return preferences.getString(AuthActivity.STUDENT_KEY, "-1") ?: "-1"
    }

    fun putStudentKey(keyString: String) {
        val editor = preferences.edit()
        editor.putString(AuthActivity.STUDENT_KEY, keyString).apply()
    }

    fun logState() {
        preferences.all.entries.forEach {
            Logger.d(it.toString())
        }
    }
}