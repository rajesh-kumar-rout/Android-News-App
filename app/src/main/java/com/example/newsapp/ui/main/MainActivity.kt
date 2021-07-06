package com.example.newsapp.ui.main

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.ui.category.CategoryFragment
import com.example.newsapp.ui.favorite.FavoriteFragment
import com.example.newsapp.ui.home.HomeFragment
import com.example.newsapp.ui.search.SearchFragment
import com.example.newsapp.util.KEY_IS_SEARCH_BAR_VISIBLE
import com.example.newsapp.util.KEY_TOOLBAR_TITLE

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            changeFragment(HomeFragment(), getString(R.string.home))
        }else{
            binding.searchBar.isVisible = savedInstanceState.getBoolean(KEY_IS_SEARCH_BAR_VISIBLE)
            binding.toolbar.isVisible = !savedInstanceState.getBoolean(KEY_IS_SEARCH_BAR_VISIBLE)
            binding.toolbar.title = savedInstanceState.getString(KEY_TOOLBAR_TITLE)
        }

        binding.bottomNav.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> changeFragment(HomeFragment(), getString(R.string.home))

                R.id.nav_search -> changeFragment(
                    SearchFragment(binding.searchView),
                    getString(R.string.Search)
                )

                R.id.nav_category -> changeFragment(
                    CategoryFragment(),
                    getString(R.string.category)
                )

                R.id.nav_favorite -> changeFragment(
                    FavoriteFragment(),
                    getString(R.string.favorite)
                )
            }
            true
        }
    }

    private fun changeFragment(fragment: Fragment, title: String) {
        binding.toolbar.isVisible = fragment !is SearchFragment
        binding.searchBar.isVisible = fragment is SearchFragment
        binding.toolbar.title = title
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putBoolean(KEY_IS_SEARCH_BAR_VISIBLE, true)
        outState.putString(KEY_TOOLBAR_TITLE, binding.toolbar.title.toString())
    }
}