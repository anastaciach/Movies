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
    var itemLiveData: LiveData<List<Item>> = galleryRepository.getMovies()

    private val omdbFetchr = OmbdFetchr()
    private val mutableSearchTerm = MutableLiveData<String>()
    val searchTerm: String get() = mutableSearchTerm.value ?: ""

    init {
        mutableSearchTerms.value = QueryPreferences.getStoredQueryByYear(app)
        galleryItemLiveData = mutableSearchTerms.switchMap { (searchTerm1, searchTerm2) ->
            omdbFetchr.searchMoviesByYear(searchTerm1, searchTerm2)
        }
    }
    fun fetchMovies(query: String = "") {
        QueryPreferences.setStoredQueryByYear(app, query,year)
        mutableSearchTerms.value = Pair(query, year)
    }
    fun getStoredQueryByYear(context: Context): Pair<String, String> {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val query = prefs.getString(PREF_SEARCH_QUERY, "")!!
        val year = prefs.getString(PREF_SEARCH_QUERY_YEAR, "")!!
        return Pair(query, year)
    }
    fun addMovie(movie: GalleryItem) {
        galleryRepository.addMovie(movie)
    }
    fun deleteMovie(){
        galleryRepository.deleteMovie(item.id)
    }
    fun updateItem(item: Item, del:Int){
        item.del = del
        galleryRepository.updateItem(item)
    }
}