package com.bignerdranch.android.movies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.sample.movies.api.OmbdFetchr

class MovieGalleryViewModel(private val app: Application
) : AndroidViewModel(app) {
    val galleryItemLiveData: LiveData<List<GalleryItem>>
    //val itemLiveData: LiveData<List<Item>> = galleryRepository.getPhotos()

    private val omdbFetchr = OmbdFetchr()
    private val mutableSearchTerm = MutableLiveData<String>()
    val searchTerm: String get() = mutableSearchTerm.value ?: ""

    init {
        mutableSearchTerm.value = QueryPreferences.getStoredQuery(app)
        galleryItemLiveData = mutableSearchTerm.switchMap { searchTerm ->
            if (searchTerm.isBlank()) {
                omdbFetchr.fetchMovies()
            } else {
                omdbFetchr.searchMovies(searchTerm)
            }
        }
    }
    fun fetchMovies(query: String = "") {
        QueryPreferences.setStoredQuery(app, query)
        mutableSearchTerm.value = query
    }
}