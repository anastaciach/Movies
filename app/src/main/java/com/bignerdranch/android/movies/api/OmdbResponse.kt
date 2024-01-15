package com.bignerdranch.android.movies.api

import com.bignerdranch.android.movies.MovieResponse

data class OmdbResponse(
    val Search: List<MovieItem>, // Используйте List<MovieItem> вместо MovieItem

)

data class MovieItem(
    var Title: String,
    var Year: String,
    var imdbID: String,
    var Type: String,
    var Poster: String
)