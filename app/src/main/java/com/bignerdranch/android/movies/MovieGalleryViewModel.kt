package com.bignerdranch.android.movies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.bignerdranch.android.movies.api.OmbdFetchr
import androidx.lifecycle.Observer
class MovieGalleryViewModel(private val app: Application
) : AndroidViewModel(app) {
    private val galleryRepository = GalleryRepository.get()
    val galleryItemLiveData: LiveData<List<GalleryItem>>
    val itemLiveData: LiveData<List<Item>> = galleryRepository.getMovies()

    private val omdbFetchr = OmbdFetchr()
    private val mutableSearchTerm = MutableLiveData<String>()
    val searchTerm: String get() = mutableSearchTerm.value ?: ""

    init {
        mutableSearchTerm.value = QueryPreferences.getStoredQuery(app)
        galleryItemLiveData = mutableSearchTerm.switchMap { searchTerm ->
            omdbFetchr.searchMovies(searchTerm)
        }
    }
    fun fetchMovies(query: String = "") {
        QueryPreferences.setStoredQuery(app, query)
        mutableSearchTerm.value = query
    }
    fun deleteMovie(){
        galleryRepository.deleteMovie(item.id)
    }
}