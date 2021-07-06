package com.example.newsapp.util.extensions

import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.util.IMAGE_URL

fun ImageView.load(imageUrl: String?, placeholder: Int = R.drawable.image_bg) {
    Glide.with(this.context).load(IMAGE_URL + imageUrl).placeholder(placeholder).into(this)
}

fun RecyclerView.onReachedEnd(onReachEndListener: () -> Unit){
    addOnScrollListener(object : RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if(dy > 0 && !recyclerView.canScrollVertically(RecyclerView.VERTICAL)) onReachEndListener()
        }
    })
}

fun SearchView.onQuerySubmit(func: (String) -> Unit){
    setOnQueryTextListener(object : SearchView.OnQueryTextListener{
        override fun onQueryTextChange(newText: String?): Boolean {return false}
        override fun onQueryTextSubmit(query: String?): Boolean {
            func(query!!)
            return false
        }
    })
}
