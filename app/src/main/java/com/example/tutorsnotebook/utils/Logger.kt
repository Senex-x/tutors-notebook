package com.example.tutorsnotebook.utils

import android.util.Log

class Logger {
    companion object {
        fun d(message: String) {
            Log.d("Debug", message)
        }
    }
}