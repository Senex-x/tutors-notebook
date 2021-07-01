package com.example.tutorsnotebook.database

import com.google.firebase.database.DataSnapshot

interface OnDataGetListener {
    fun onSuccess(data: DataSnapshot?)
}