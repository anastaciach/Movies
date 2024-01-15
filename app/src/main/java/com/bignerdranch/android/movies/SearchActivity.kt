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
        val title = intent.getStringExtra("addgalleryItemTitle")
        val year = intent.getStringExtra("addgalleryItemYear")
        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.searchFragmentContainer, SearchFragment.newInstance(title.toString(),year.toString()))
                .commit()
        }
    }
    override fun onSelected(galleryItem: GalleryItem){
        val intent = Intent(this, AddActivity::class.java)
        intent.putExtra("fragmentToOpen", "lastFragment")
        intent.putExtra("galleryItemId", galleryItem.imdbID)
        intent.putExtra("galleryItemUrl", galleryItem.Poster)
        intent.putExtra("galleryItemYear", galleryItem.Year)
        intent.putExtra("galleryItemTitle", galleryItem.Title)
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