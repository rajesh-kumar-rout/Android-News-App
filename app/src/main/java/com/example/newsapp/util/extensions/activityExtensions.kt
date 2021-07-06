package com.example.newsapp.util.extensions

import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.setTitleAndHomeButton(title: String){
    this.title = title
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
}