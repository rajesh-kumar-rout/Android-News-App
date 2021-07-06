package com.example.newsapp.ui.postDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.models.Post
import com.example.newsapp.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostDetailsViewModel(
    private val post: Post
) : ViewModel() {

    private val stateIsFavoritePost: MutableLiveData<Boolean> = MutableLiveData()
    val stateIsFavoritePostLiveData: LiveData<Boolean> = stateIsFavoritePost

    init {
        checkIsFavorite()
    }

    private fun checkIsFavorite() = viewModelScope.launch(Dispatchers.IO) {
        stateIsFavoritePost.postValue(Repository.isFavoritePost(post.id))
    }

    fun addToFavorite() = viewModelScope.launch(Dispatchers.IO) {
        Repository.addToFavorite(post)
        stateIsFavoritePost.postValue(true)
    }

    fun removeFavorite() = viewModelScope.launch(Dispatchers.IO) {
        Repository.remoteFavorite(post)
        stateIsFavoritePost.postValue(false)
    }
}