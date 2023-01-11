package com.example.kotlinapp.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinapp.AppState
import com.example.kotlinapp.model.dto.Films

class MovieDetailsViewModel (
    private val liveDataMovie: MutableLiveData<Films> = MutableLiveData()
        ) : ViewModel() {

    fun getLiveDataMovie() = liveDataMovie

    fun setLiveDataMovie(movie: Films) {
        liveDataMovie.value = movie
    }
}