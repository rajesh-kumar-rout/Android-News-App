package com.example.newsapp.data.network

import com.example.newsapp.data.models.Category
import com.example.newsapp.data.models.Post
import com.example.newsapp.data.models.PostResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkDataSource {

    @GET("post")
    suspend fun getPosts(
        @Query("start") start: Int,
        @Query("limit") limit: Int
    ): Response<MutableList<PostResponse>>

    @GET("post/{category}/category")
    suspend fun getPosts(
        @Query("start") start: Int,
        @Query("limit") limit: Int,
        @Path("category") categoryId: Int
    ): Response<MutableList<PostResponse>>

    @GET("post/search")
    suspend fun getPosts(
        @Query("start") start: Int,
        @Query("limit") limit: Int,
        @Query("query") query: String
    ): Response<MutableList<PostResponse>>

    @GET("category")
    suspend fun getCategories(): Response<MutableList<Category>>
}