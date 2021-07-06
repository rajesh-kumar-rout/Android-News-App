package com.example.newsapp.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Category(
    val id: Int,
    val name: String,

    @SerializedName("image_url")
    val imageUrl: String
): Serializable