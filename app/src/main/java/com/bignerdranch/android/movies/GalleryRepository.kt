package com.bignerdranch.android.movies

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import org.json.JSONObject.NULL
import java.util.UUID
import java.util.concurrent.Executors
import com.bignerdranch.android.movies.Item

private const val DATABASE_NAME = "gallery"
class GalleryRepository private constructor(context: Context) {
    private val database: GalleryDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            GalleryDatabase::class.java,
            DATABASE_NAME
        )
        .build()
    private val executor = Executors.newSingleThreadExecutor()
    fun getMovies(): LiveData<List<Item>> = database.galleryDao().getmovies()
    private fun GalleryItem.toItem(): Item {
        return Item(Title, Year, Poster, imdbID,0)
    }

    fun addMovie(movie: GalleryItem) {
        var item = Item()
        item.id = movie.imdbID
        item.title = movie.Title
        item.year = movie.Year
        item.url = movie.Poster
        executor.execute {
            if (database.galleryDao().getmovie(item.id)!=item){
                database.galleryDao().addmovie(item)
            }
        }
    }
    fun updateItem(item: Item){
        executor.execute {
            database.galleryDao().updateItem(item)
        }
    }
    fun deleteMovie() {
        executor.execute {
            database.galleryDao().deletemovie()
        }
    }

    companion object {
        private var INSTANCE: GalleryRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = GalleryRepository(context)
            }
        }
        fun get(): GalleryRepository {
            return INSTANCE ?:
            throw
            IllegalStateException("Repository must be initialized")
        }
    }

}