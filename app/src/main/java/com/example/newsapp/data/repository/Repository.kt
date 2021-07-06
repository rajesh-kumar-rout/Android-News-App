package com.example.newsapp.data.repository

import androidx.lifecycle.LiveData
import com.example.newsapp.data.local.database
import com.example.newsapp.data.models.Category
import com.example.newsapp.data.models.Post
import com.example.newsapp.data.network.fetch
import com.example.newsapp.data.network.networkDataSource
import com.example.newsapp.util.PAGINATION_PER_PAGE
import com.example.newsapp.util.mapper.PostMapper

object Repository {

    suspend fun getPosts(start: Int): MutableList<Post>{
        return PostMapper.fromList(fetch { networkDataSource.getPosts(start, PAGINATION_PER_PAGE) })
    }

    suspend fun getCategories(): MutableList<Category>{
        return fetch { networkDataSource.getCategories() }
    }

    suspend fun getPosts(start: Int, categoryId: Int): MutableList<Post>{
        return PostMapper.fromList(fetch { networkDataSource.getPosts(start, categoryId) })
    }

    suspend fun getFavoritePosts(): LiveData<MutableList<Post>>{
        return database.postDao().getAll()
    }

    suspend fun addToFavorite(post: Post){
        database.postDao().insert(post)
    }

    suspend fun remoteFavorite(post: Post){
        database.postDao().delete(post)
    }

    suspend fun isFavoritePost(postId: Int): Boolean{
        return database.postDao().isPostExist(postId)
    }

    suspend fun getPosts(start: Int, query: String): MutableList<Post>{
        return PostMapper.fromList(fetch { networkDataSource.getPosts(start, PAGINATION_PER_PAGE, query) })
    }
}