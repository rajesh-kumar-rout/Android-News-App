package com.example.newsapp.ui.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.R
import com.example.newsapp.databinding.LayoutRecyclerviewBinding
import com.example.newsapp.util.adapters.PostAdapter
import com.example.newsapp.util.extensions.*

class SearchFragment(
    private val searchView: SearchView
): Fragment(R.layout.layout_recyclerview) {

    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: PostAdapter
    private lateinit var binding: LayoutRecyclerviewBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = LayoutRecyclerviewBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
        adapter = PostAdapter(requireContext(), viewModel::getPosts)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.onReachedEnd(viewModel::getNextPage)
        binding.tvEmpty.text = getString(R.string.msg_no_posts)
        binding.refreshLayout.isEnabled = false
        binding.btnRetry.setOnClickListener { viewModel.getPosts() }

        searchView.onQuerySubmit { searchQuery ->
            viewModel.searchQuery = searchQuery
            viewModel.getPosts()
        }

        viewModel.paginatedPostsLiveData.observe(viewLifecycleOwner, Observer { paginatedPosts ->
            binding.layoutError.isVisible = paginatedPosts.isInitialError
            binding.layoutProgressbar.isVisible = paginatedPosts.isInitialLoading
            binding.tvError.text = paginatedPosts.errorMessage
            binding.tvEmpty.isVisible = paginatedPosts.isInitialEmpty
            binding.recyclerview.post { adapter.setData(paginatedPosts) }
        })
    }
}