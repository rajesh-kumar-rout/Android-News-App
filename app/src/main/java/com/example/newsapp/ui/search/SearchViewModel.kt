package com.example.newsapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.models.PaginatedData
import com.example.newsapp.data.models.PaginationStatus
import com.example.newsapp.data.models.Post
import com.example.newsapp.data.repository.Repository
import kotlinx.coroutines.*

class SearchViewModel : ViewModel() {

    private var searchJob: Job? = null
    private val paginatedPosts: MutableLiveData<PaginatedData<Post>> = MutableLiveData(PaginatedData())
    val paginatedPostsLiveData: LiveData<PaginatedData<Post>> = paginatedPosts
    var searchQuery: String = String()

    fun getNextPage() {
        if (paginatedPosts.value!!.isLoadingOrFinished) return
        paginatedPosts.value = paginatedPosts.value?.stateLoading()
        fetchPosts()
    }

    fun getPosts() {
        paginatedPosts.value = PaginatedData(PaginationStatus.LOADING)
        searchJob?.cancel()
        fetchPosts()
    }

    private fun fetchPosts() {
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                val posts: MutableList<Post> = Repository.getPosts(paginatedPosts.value!!.data.size, searchQuery)
                paginatedPosts.postValue(paginatedPosts.value?.stateSuccess(posts))
            } catch (exception: Exception) {
                paginatedPosts.postValue(paginatedPosts.value?.stateFailure(exception))
            }
        }
    }
}