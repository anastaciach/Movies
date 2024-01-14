package com.bignerdranch.android.movies

import android.app.Application

class MovieGalleryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        GalleryRepository.initialize(this)
    }
}