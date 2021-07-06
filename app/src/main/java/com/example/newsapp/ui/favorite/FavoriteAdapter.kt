package com.example.newsapp.ui.favorite

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.data.models.Post
import com.example.newsapp.databinding.PostBinding
import com.example.newsapp.ui.postDetails.PostDetailsActivity
import com.example.newsapp.util.EXTRA_POST
import com.example.newsapp.util.extensions.load

class FavoriteAdapter(
    private val context: Context,
    private var favoritePosts: MutableList<Post> = mutableListOf()
): RecyclerView.Adapter<FavoriteAdapter.PostViewHolder>() {

    inner class PostViewHolder(private val postBinding: PostBinding): RecyclerView.ViewHolder(postBinding.root){
        init {
            postBinding.root.setOnClickListener { onPostClick(adapterPosition) }
        }

        fun bind(post: Post){
            postBinding.title.text = post.title
            postBinding.createdAt.text = post.createdAt
            postBinding.image.load(post.imageUrl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            PostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return favoritePosts.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(favoritePosts[position])
    }

    private fun onPostClick(position: Int){
        val intent = Intent(context, PostDetailsActivity::class.java)
        intent.putExtra(EXTRA_POST, favoritePosts[position])
        context.startActivity(intent)
    }

    fun setData(favoritePosts: MutableList<Post>){
        this.favoritePosts = favoritePosts
        notifyDataSetChanged()
    }
}