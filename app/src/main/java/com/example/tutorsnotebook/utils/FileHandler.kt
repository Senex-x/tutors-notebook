package com.example.tutorsnotebook.utils

import android.app.Activity
import android.content.Context
import android.net.Uri
import java.io.FileNotFoundException
import java.util.*

class FileHandler(private val context: Context) {
    companion object {
        const val CALENDAR_STATE_FILE_NAME = "calendar-state.txt"
    }

    fun saveTextToFile(fileName: String, text: String) {

        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(text.toByteArray())
        }

    }

    fun getTextFromFileByUri(txtUri: Uri, activity: Activity): String {
        var result = ""
        try {
            val inputStream = activity.contentResolver.openInputStream(txtUri)
            val s: Scanner = Scanner(inputStream).useDelimiter("\\A")
            result = if (s.hasNext()) s.next() else ""
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return result
    }

    fun getTextFromFile(fileName: String): String {
        val text = StringBuilder()
        try {
            context.openFileInput(fileName).bufferedReader().useLines { lines ->
                lines.forEach {
                    text.append(it).append("\n")
                }
            }
        } catch (e: FileNotFoundException) {
            saveTextToFile(fileName, "") // creating file
            getTextFromFile(fileName) // restart
        }
        return text.toString()
    }

    fun deleteFile(fileName: String) {
        Logger.d("Trying to delete file $fileName \nResult: ${if (context.deleteFile(fileName)) "OK" else "ERROR"}")
    }
}