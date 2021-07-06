package com.example.newsapp.ui.post

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.R
import com.example.newsapp.data.models.Category
import com.example.newsapp.databinding.LayoutRecyclerviewBinding
import com.example.newsapp.util.EXTRA_CATEGORY
import com.example.newsapp.util.adapters.PostAdapter
import com.example.newsapp.util.extensions.*

class PostActivity: AppCompatActivity() {

    private lateinit var binding: LayoutRecyclerviewBinding
    private lateinit var viewModel: PostViewModel
    private lateinit var adapter: PostAdapter

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutRecyclerviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val category = intent.getSerializableExtra(EXTRA_CATEGORY) as Category
        setTitleAndHomeButton(category.name)
        viewModel = ViewModelProvider(this, PostViewModelFactory(category.id)).get(PostViewModel::class.java)
        viewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        adapter = PostAdapter(this, viewModel::getPosts)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.onReachedEnd(viewModel::getPosts)
        binding.refreshLayout.isEnabled = false
        binding.tvEmpty.text = getString(R.string.msg_no_posts)

        viewModel.paginatedPostsLiveData.observe(this, Observer { paginatedPosts ->
            binding.layoutError.isVisible = paginatedPosts.isInitialError
            binding.layoutProgressbar.isVisible = paginatedPosts.isInitialLoading
            binding.tvEmpty.isVisible = paginatedPosts.isInitialEmpty
            binding.tvError.text = paginatedPosts.errorMessage
            binding.recyclerview.post { adapter.setData(paginatedPosts) }
        })
    }
}