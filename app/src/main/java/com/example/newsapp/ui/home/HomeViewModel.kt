package com.example.newsapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.models.Event
import com.example.newsapp.data.models.PaginatedData
import com.example.newsapp.data.models.PaginationStatus
import com.example.newsapp.data.models.Post
import com.example.newsapp.data.network.fetch
import com.example.newsapp.data.repository.Repository
import com.example.newsapp.util.CommunicationException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private var refreshingJob: Job? = null

    private val paginatedPosts: MutableLiveData<PaginatedData<Post>> =
        MutableLiveData(PaginatedData())
    private val refreshingError: MutableLiveData<Event<String>> = MutableLiveData()
    val paginatedPostsLiveData: LiveData<PaginatedData<Post>> = paginatedPosts
    val refreshingErrorLiveData: LiveData<Event<String>> = refreshingError

    init {
        getPosts()
    }

    fun refresh() {
        refreshingJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                val posts = Repository.getPosts(0)
                paginatedPosts.postValue(paginatedPosts.value?.stateRefreshed(posts))
            } catch (exception: Exception) {
                if (exception is CancellationException) return@launch
                refreshingError.postValue(Event(exception.message))
            }
        }
    }

    fun getPosts() {
        if (paginatedPosts.value!!.isLoadingOrFinished) return

        refreshingJob?.cancel()

        viewModelScope.launch(Dispatchers.IO) {
            paginatedPosts.postValue(paginatedPosts.value?.stateLoading())
            try {
                val posts: MutableList<Post> = Repository.getPosts(paginatedPosts.value!!.data.size)
                paginatedPosts.postValue(paginatedPosts.value?.stateSuccess(posts))
            } catch (exception: Exception) {
                paginatedPosts.postValue(paginatedPosts.value?.stateFailure(exception))
            }
        }
    }
}