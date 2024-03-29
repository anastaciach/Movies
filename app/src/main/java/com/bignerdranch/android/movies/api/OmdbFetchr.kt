package com.bignerdranch.android.movies.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bignerdranch.android.movies.GalleryItem
import com.bignerdranch.android.movies.MovieInterceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val TAG = "OmdbFetchr"
class OmbdFetchr {
    private val omdbApi: OmdbApi
    init {
        val client = OkHttpClient.Builder()
            .addInterceptor(MovieInterceptor())
            .build()
        val retrofit: Retrofit =
            Retrofit.Builder()
                .baseUrl("https://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        omdbApi = retrofit.create(OmdbApi::class.java)
    }

    fun searchMoviesRequest(query: String):
            Call<OmdbResponse> {
        return omdbApi.searchMovies(query)
    }
    fun searchMovies(query: String):
            LiveData<List<GalleryItem>> {
        return fetchMovieMetadata(searchMoviesRequest(query))
    }

    private fun fetchMovieMetadata(omdbRequest: Call<OmdbResponse>)
            : LiveData<List<GalleryItem>> {
        val responseLiveData:
                MutableLiveData<List<GalleryItem>> = MutableLiveData()
        omdbRequest.enqueue(object :
            Callback<OmdbResponse> {
            override fun onFailure(call: Call<OmdbResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch movies", t)
            }
            override fun onResponse(
                call: Call<OmdbResponse>,
                response: Response<OmdbResponse>
            ) {
                Log.d(TAG, "Response received")
                val omdbResponse: OmdbResponse? = response.body()
                val movieItems: List<MovieItem>? = omdbResponse?.Search

                val galleryItems: List<GalleryItem> = movieItems?.map {
                    GalleryItem(it.Title, it.Year,it.imdbID,it.Type,it.Poster)
                }.orEmpty().filterNot {
                    it.Title.isBlank()
                }
                responseLiveData.value = galleryItems}
            })
        return responseLiveData
    }
}