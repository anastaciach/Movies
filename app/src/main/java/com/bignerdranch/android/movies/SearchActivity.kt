package com.bignerdranch.android.movies

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.sample.movies.R
private const val TAG = "SearchActivity"
class SearchActivity : AppCompatActivity(),SearchFragment.Callbacks{
    private lateinit var movieGalleryViewModel: MovieGalleryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.searchFragmentContainer, SearchFragment.newInstance())
                .commit()
        }
    }
    override fun onSelected(galleryItem: GalleryItem){
        val intent = Intent(this, AddActivity::class.java)
        intent.putExtra("fragmentToOpen", "lastFragment")
        intent.putExtra("galleryItemId", galleryItem.id)
        intent.putExtra("galleryItemUrl", galleryItem.url)
        intent.putExtra("galleryItemYear", galleryItem.year)
        intent.putExtra("galleryItemTitle", galleryItem.title)
        startActivity(intent)
        movieGalleryViewModel =
            ViewModelProviders.of(this).get(MovieGalleryViewModel::class.java)
        val isFragmentContainerEmpty =
            savedInstanceState == null
        if (isFragmentContainerEmpty) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, MovieGalleryFragment.newInstance())
                .commit()
        }
    }

    companion object {
        fun newIntent(context: Context): Intent
        {
            return Intent(context, SearchActivity::class.java)
        }
    }

}