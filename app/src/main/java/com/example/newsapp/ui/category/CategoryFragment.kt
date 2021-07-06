package com.example.newsapp.ui.category

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.R
import com.example.newsapp.databinding.LayoutRecyclerviewBinding

class CategoryFragment: Fragment(R.layout.layout_recyclerview) {

    private lateinit var binding: LayoutRecyclerviewBinding
    private lateinit var viewModel: CategoryViewModel
    private lateinit var adapter: CategoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = LayoutRecyclerviewBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity()).get(CategoryViewModel::class.java)
        adapter = CategoryAdapter(requireContext())
        binding.recyclerview.adapter = adapter
        binding.tvEmpty.text = getString(R.string.msg_no_category)
        binding.refreshLayout.isEnabled = false
        binding.btnRetry.setOnClickListener { viewModel.getCategories() }

        viewModel.stateLoadingLiveData.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.layoutProgressbar.isVisible = isLoading
        })

        viewModel.stateErrorLiveData.observe(viewLifecycleOwner, Observer { errorMessage ->
            binding.layoutError.isVisible = true
            binding.tvError.text = errorMessage
        })

        viewModel.categoriesLiveData.observe(viewLifecycleOwner, Observer { categories ->
            binding.tvEmpty.isVisible = categories.size == 0
            adapter.setData(categories)
        })
    }
}