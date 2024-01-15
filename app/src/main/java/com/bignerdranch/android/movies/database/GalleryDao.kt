package com.bignerdranch.android.movies.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bignerdranch.android.movies.Item

@Dao

interface GalleryDao {
    @Query("SELECT * FROM gallery")
    fun getmovies(): LiveData<List<Item>>
    @Insert
    fun addmovie(item: Item)

    @Query("DELETE FROM gallery WHERE del = 1")
    fun deletemovie(id: String)

    @Query("SELECT * FROM gallery WHERE id=(:id)")
    fun getmovie(id: String): Item?
}