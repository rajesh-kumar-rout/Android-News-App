package com.example.newsapp.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.models.Category
import com.example.newsapp.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {

    private val categories: MutableLiveData<MutableList<Category>> = MutableLiveData()
    private val stateLoading: MutableLiveData<Boolean> = MutableLiveData()
    private val stateError: MutableLiveData<String> = MutableLiveData()

    val categoriesLiveData: LiveData<MutableList<Category>> = categories
    val stateLoadingLiveData: LiveData<Boolean> = stateLoading
    val stateErrorLiveData: LiveData<String> = stateError

    init {
        getCategories()
    }

    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            stateLoading.postValue(true)
            try {
                categories.postValue(Repository.getCategories())
            } catch (exception: Exception) {
                stateError.postValue(exception.message)
            }
            stateLoading.postValue(false)
        }
    }
}