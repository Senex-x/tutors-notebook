package com.example.tutorsnotebook.database

interface OnObjectGetListener<T> {
    fun onSuccess(item: T)
}