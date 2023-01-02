package com.example.kotlinapp

import com.example.kotlinapp.model.Movie

sealed class AppState {
    data class Success(val movieData : List<Movie>) : AppState()
    data class Error(val error : Throwable) : AppState()
    object Loading : AppState()
}
