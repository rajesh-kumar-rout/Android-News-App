package com.example.newsapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.newsapp.data.models.Post

@Dao
interface PostDao {
    @Query("SELECT * FROM post")
    fun getAll(): LiveData<MutableList<Post>>

    @Query("SELECT EXISTS(SELECT * FROM post WHERE id = :postId)")
    fun isPostExist(postId: Int): Boolean

    @Insert
    fun insert(post: Post)

    @Delete
    fun delete(post: Post)
}