package com.bignerdranch.android.movies

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "gallery")

data class Item(
    var title: String = "",
    var year: String = "",
    var url: String = "",
    @PrimaryKey var id: String = "",
    var delete: Boolean = 0
)