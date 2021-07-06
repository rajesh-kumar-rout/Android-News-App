package com.example.newsapp.data.models

class Event<T>(
    private val _data: T?
) {
    private var isHandled: Boolean = false

    val data: T? get() = if(isHandled) null else {
        isHandled = true
        _data
    }
}