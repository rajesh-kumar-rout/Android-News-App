package com.example.newsapp.ui.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.models.PaginatedData
import com.example.newsapp.data.models.PaginationStatus
import com.example.newsapp.data.models.Post
import com.example.newsapp.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostViewModel(
    private val categoryId: Int
) : ViewModel() {

    private val paginatedPosts: MutableLiveData<PaginatedData<Post>> = MutableLiveData(PaginatedData())
    val paginatedPostsLiveData: LiveData<PaginatedData<Post>> = paginatedPosts

    init {
        getPosts()
    }

    fun getPosts() {
        if(paginatedPosts.value!!.isLoadingOrFinished) return

        paginatedPosts.value = paginatedPosts.value?.stateLoading()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val posts: MutableList<Post> = Repository.getPosts(paginatedPosts.value!!.data.size, categoryId)
                paginatedPosts.postValue(paginatedPosts.value?.stateSuccess(posts))
            } catch (exception: Exception) {
                paginatedPosts.postValue(paginatedPosts.value?.stateFailure(exception))
            }
        }
    }
}