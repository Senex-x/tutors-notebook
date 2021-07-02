package com.example.tutorsnotebook.database

interface OnItemGetListener<T> {
    fun onSuccess(item: T)
}