package com.example.tutorsnotebook.utils

import android.content.Context
import android.widget.Toast

object Toaster {
    fun toast(message: String, context: Context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}