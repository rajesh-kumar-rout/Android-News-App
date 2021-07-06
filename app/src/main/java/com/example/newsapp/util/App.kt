package com.example.newsapp.util

import android.app.Application
import android.content.Context

class App: Application() {

    companion object{
        public lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}