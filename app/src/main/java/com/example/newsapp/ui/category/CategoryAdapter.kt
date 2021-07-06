package com.example.newsapp.ui.category

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.data.models.Category
import com.example.newsapp.databinding.CategoryBinding
import com.example.newsapp.ui.post.PostActivity
import com.example.newsapp.ui.postDetails.PostDetailsActivity
import com.example.newsapp.util.EXTRA_CATEGORY
import com.example.newsapp.util.EXTRA_POST
import com.example.newsapp.util.extensions.load

class CategoryAdapter(
    private val context: Context,
    private val categories: MutableList<Category> = mutableListOf()
): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(private val categoryBinding: CategoryBinding): RecyclerView.ViewHolder(categoryBinding.root){
        init {
            categoryBinding.root.setOnClickListener { onCategoryClick(adapterPosition) }
        }

        fun bind(category: Category){
            categoryBinding.tvName.text = category.name
            categoryBinding.image.load(category.imageUrl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            CategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    fun setData(categories: MutableList<Category>){
        this.categories.addAll(categories)
        notifyDataSetChanged()
    }

    private fun onCategoryClick(position: Int){
        val intent = Intent(context, PostActivity::class.java)
        intent.putExtra(EXTRA_CATEGORY, categories[position])
        context.startActivity(intent)
    }
}