package com.example.newsapp.util

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.data.models.PaginationStatus
import com.example.newsapp.databinding.PagingFooterBinding

class PagingFooterViewHolder(
    private val binding: PagingFooterBinding,
    private val retryClickListener: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnRetry.setOnClickListener { retryClickListener() }
    }

    fun bind(status: PaginationStatus) {
        binding.progressbar.isVisible = status == PaginationStatus.LOADING
        binding.btnRetry.isVisible = status == PaginationStatus.FAILURE
        if(status == PaginationStatus.FINISHED) binding.root.layoutParams.height = 0
    }
}