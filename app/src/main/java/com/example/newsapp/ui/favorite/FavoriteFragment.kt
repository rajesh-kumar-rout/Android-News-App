package com.example.newsapp.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.R
import com.example.newsapp.databinding.LayoutRecyclerviewBinding

class FavoriteFragment: Fragment(R.layout.layout_recyclerview) {

    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: FavoriteAdapter
    private lateinit var binding: LayoutRecyclerviewBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = LayoutRecyclerviewBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity()).get(FavoriteViewModel::class.java)
        adapter = FavoriteAdapter(requireContext())
        binding.recyclerview.adapter = adapter
        binding.refreshLayout.isEnabled = false
        binding.tvEmpty.text = getString(R.string.msg_no_posts)

        viewModel.favoritePostsLiveData.observe(viewLifecycleOwner, Observer { posts ->
            binding.tvEmpty.isVisible = posts.size == 0
            adapter.setData(posts)
        })
    }
}
