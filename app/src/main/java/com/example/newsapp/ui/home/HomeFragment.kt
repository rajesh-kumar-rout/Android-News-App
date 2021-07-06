package com.example.newsapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.R
import com.example.newsapp.databinding.LayoutRecyclerviewBinding
import com.example.newsapp.util.extensions.*

class HomeFragment : Fragment(R.layout.layout_recyclerview) {

    private lateinit var binding: LayoutRecyclerviewBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: HomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = LayoutRecyclerviewBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        adapter = HomeAdapter(requireContext(), viewModel::getPosts)
        binding.recyclerview.onReachedEnd(viewModel::getPosts)
        binding.recyclerview.adapter = adapter
        binding.tvEmpty.text = getString(R.string.msg_no_posts)
        binding.btnRetry.setOnClickListener { viewModel.getPosts() }

        binding.refreshLayout.setOnRefreshListener{ viewModel.refresh() }

        viewModel.refreshingErrorLiveData.observe(viewLifecycleOwner, Observer { error ->
            binding.refreshLayout.isRefreshing = false
            error.data?.let { showToast(it) }
        })

        viewModel.paginatedPostsLiveData.observe(viewLifecycleOwner, Observer { paginatedPosts ->
            binding.refreshLayout.isRefreshing = false
            binding.layoutError.isVisible = paginatedPosts.isInitialError
            binding.layoutProgressbar.isVisible = paginatedPosts.isInitialLoading
            binding.tvError.text = paginatedPosts.errorMessage
            binding.tvEmpty.isVisible = paginatedPosts.isInitialEmpty
            binding.recyclerview.post { adapter.setData(paginatedPosts) }
        })
    }
}