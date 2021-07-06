package com.example.newsapp.data.local

import androidx.room.Room
import com.example.newsapp.util.App
import com.example.newsapp.util.DATABASE_NAME

val database: AppDatabase = Room.databaseBuilder(
    App.context,
    AppDatabase::class.java,
    DATABASE_NAME
).build()