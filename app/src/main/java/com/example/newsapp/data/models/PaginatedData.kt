package com.example.newsapp.data.models

import java.lang.Exception

class PaginatedData<T>(
    var status: PaginationStatus = PaginationStatus.INITIAL,
    var data: MutableList<T> = mutableListOf(),
    var errorMessage: String = String()
){
    fun stateLoading(): PaginatedData<T> {
        this.status = PaginationStatus.LOADING
        return this
    }

    fun stateRefreshed(data: MutableList<T>): PaginatedData<T>{
        this.data.clear()
        this.status = if (data.isEmpty()) PaginationStatus.FINISHED else PaginationStatus.SUCCESS
        this.data.addAll(data)
        return this
    }

    fun stateSuccess(data: MutableList<T>): PaginatedData<T> {
        this.status = if (data.isEmpty()) PaginationStatus.FINISHED else PaginationStatus.SUCCESS
        this.data.addAll(data)
        return this
    }

    fun stateFailure(exception: Exception): PaginatedData<T> {
        this.status = PaginationStatus.FAILURE
        this.errorMessage = exception.message!!
        return this
    }

    val isInitialError: Boolean get() = data.isEmpty() && status == PaginationStatus.FAILURE

    val isInitialLoading: Boolean get() = data.isEmpty() && status == PaginationStatus.LOADING

    val isLoadingOrFinished: Boolean get() = status == PaginationStatus.LOADING || status == PaginationStatus.FINISHED

    val isInitialEmpty: Boolean get() = data.isEmpty() && status == PaginationStatus.FINISHED
}