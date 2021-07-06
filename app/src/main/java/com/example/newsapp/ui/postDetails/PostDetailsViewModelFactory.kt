package com.example.newsapp.ui.postDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.data.models.Post

class PostDetailsViewModelFactory(
    private val post: Post
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostDetailsViewModel(post) as T
    }
}