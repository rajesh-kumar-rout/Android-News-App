package com.example.newsapp.util.mapper

import com.example.newsapp.data.models.Post
import com.example.newsapp.data.models.PostResponse
import com.example.newsapp.util.DateConverter

object PostMapper {

    fun fromList(postResponses: MutableList<PostResponse>): MutableList<Post> {
        val posts: MutableList<Post> = mutableListOf()
        postResponses.map { postResponse ->
            posts.add(
                Post(
                    postResponse.id,
                    postResponse.title,
                    postResponse.description,
                    postResponse.imageUrl,
                    DateConverter.convertToRelativeTime(postResponse.createdAt)
                )
            )
        }
        return posts
    }
}