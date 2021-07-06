package com.example.newsapp.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.models.Post
import com.example.newsapp.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModel : ViewModel() {

    private val favoritePosts: MutableLiveData<MutableList<Post>> = MutableLiveData()
    var favoritePostsLiveData: LiveData<MutableList<Post>> = favoritePosts

    init {
        getPosts()
    }

    private fun getPosts() = viewModelScope.launch(Dispatchers.IO) {
        val postLiveData = Repository.getFavoritePosts()
        withContext(Dispatchers.Main){
            postLiveData.observeForever { posts ->
                favoritePosts.postValue(posts)
            }
        }
    }
}