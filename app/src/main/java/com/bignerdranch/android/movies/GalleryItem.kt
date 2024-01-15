package com.bignerdranch.android.movies
import com.google.gson.annotations.SerializedName
data class GalleryItem(
    var Title: String = "",
    var Year: String = "",
    var Type: String = "",
    var imdbID: String = "",
    @SerializedName("Poster") var Poster: String = ""
)