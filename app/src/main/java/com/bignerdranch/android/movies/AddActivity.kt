package com.bignerdranch.android.movies

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders

class AddActivity : AppCompatActivity(),BeforeAddFragment.Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        movieGalleryViewModel = ViewModelProviders.of(this).get(MovieGalleryViewModel::class.java)
        val fragmentToOpen = intent.getStringExtra("fragmentToOpen")

        if (fragmentToOpen == "lastFragment") {
            val id = intent.getStringExtra("galleryItemId")
            val title = intent.getStringExtra("galleryItemTitle")
            val url = intent.getStringExtra("galleryItemUrl")
            val year = intent.getStringExtra("galleryItemYear")

            val fragment = AfterAddFragment().apply {
                arguments = Bundle().apply {
                    putString("galleryItemId", id)
                    putString("galleryItemTitle", title)
                    putString("galleryItemUrl", url)
                    putString("galleryItemYear", year)
                }
            }
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerAdd, fragment)

                .commit()
        }
        else
        {
            val isFragmentContainerEmpty =
                savedInstanceState == null
            if (isFragmentContainerEmpty) {
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentContainerAdd, BeforeAddFragment.newInstance())
                    .commit()
            }
        }
    }
    override fun onSearch() {
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }
    override fun onAddBefore() {
        val fragment = AfterAddFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainerAdd, fragment)
            .addToBackStack(null)
            .commit()
    }
    override fun onAdd(galleryItem: GalleryItem) {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        movieGalleryViewModel.addMovie(galleryItem)
        startActivity(intent)
    }

    companion object {
        fun newIntent(context: Context): Intent
        {
            return Intent(context, AddActivity::class.java)
        }
    }

    override fun noItems() {
    }
}