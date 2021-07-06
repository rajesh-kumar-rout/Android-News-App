package com.example.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapp.data.models.Post
import com.example.newsapp.util.DATABASE_VERSION

@Database(entities = [Post::class], version = DATABASE_VERSION)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}
