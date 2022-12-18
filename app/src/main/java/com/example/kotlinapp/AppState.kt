package com.example.kotlinapp

sealed class AppState {
    data class Success(val movieData : Movie) : AppState()
    data class Error(val error : Throwable) : AppState()
    object Loading : AppState()
}
