package com.example.newsapp.data.models

import com.google.gson.annotations.SerializedName

data class PostResponse (
    val id: Int,
    val title: String,
    val description: String,

    @SerializedName("image_url")
    val imageUrl: String,

    @SerializedName("created_at")
    val createdAt: String
)