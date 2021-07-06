package com.example.newsapp.ui.postDetails

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.R
import com.example.newsapp.data.models.Post
import com.example.newsapp.databinding.ActivityPostDetailsBinding
import com.example.newsapp.util.EXTRA_POST
import com.example.newsapp.util.extensions.load

class PostDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostDetailsBinding
    private lateinit var viewModel: PostDetailsViewModel
    private lateinit var menu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        menu = binding.toolbar.menu
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favorite -> viewModel.removeFavorite()
                R.id.not_favorite -> viewModel.addToFavorite()
            }
            true
        }

        val post = intent.getSerializableExtra(EXTRA_POST) as Post
        binding.title.text = post.title
        binding.createdAt.text = post.createdAt
        binding.description.loadDataWithBaseURL(null, post.description, "text/html", "UTF-8", null)
        binding.image.load(post.imageUrl)

        viewModel = ViewModelProvider(this, PostDetailsViewModelFactory(post)).get(PostDetailsViewModel::class.java)

        viewModel.stateIsFavoritePostLiveData.observe(this, Observer { isFavoritePost ->
            menu.findItem(R.id.favorite).isVisible = isFavoritePost
            menu.findItem(R.id.not_favorite).isVisible = !isFavoritePost
        })
    }
}